package com.example.case_study_m4_team1.controller.teacher;

import com.example.case_study_m4_team1.dto.teacher.TeacherNoticeDTO;
import com.example.case_study_m4_team1.dto.teacher.TeacherReviewDTO;
import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.entity.Teacher;
import com.example.case_study_m4_team1.service.teacher.ITeacherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    // Session keys
    private static final String TEACHER_ID_SESSION_KEY = "teacherId";
    private static final String USERNAME_SESSION_KEY = "username";
    private static final String USER_ROLE_SESSION_KEY = "userRole";

    private boolean isTeacherLoggedIn(HttpSession session) {
        String username = (String) session.getAttribute(USERNAME_SESSION_KEY);
        String role = (String) session.getAttribute(USER_ROLE_SESSION_KEY);
        return username != null && "TEACHER".equals(role);
    }

    private Teacher getCurrentTeacher(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isTeacherLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập với tài khoản giảng viên");
            return null;
        }

        String username = (String) session.getAttribute(USERNAME_SESSION_KEY);
        Teacher teacher = teacherService.findByUsername(username);

        if (teacher == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin giảng viên");
            return null;
        }

        return teacher;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Teacher teacher = getCurrentTeacher(session, redirectAttributes);
        if (teacher == null) {
            return "redirect:/login";
        }

        model.addAttribute("teacher", teacher);
        return "teacher/dashboard";
    }

    @GetMapping("/classes")
    public String viewMyClasses(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Teacher teacher = getCurrentTeacher(session, redirectAttributes);
        if (teacher == null) {
            return "redirect:/login";
        }

        List<?> schedules = teacherService.getTeacherSchedules(teacher.getId());
        model.addAttribute("schedules", schedules);
        return "teacher/class_list";
    }

    @GetMapping("/class/{scheduleId}/students")
    public String viewStudentsInClass(@PathVariable int scheduleId,
                                      HttpSession session,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        Teacher teacher = getCurrentTeacher(session, redirectAttributes);
        if (teacher == null) {
            return "redirect:/login";
        }

        // Kiểm tra quyền: giảng viên này có dạy lớp này không
        if (!teacherService.isTeacherOfClass(teacher.getName(), scheduleId)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xem lớp học này");
            return "redirect:/teacher/dashboard";
        }

        List<ClassRegister> students = teacherService.getStudentsInClass(scheduleId);
        model.addAttribute("students", students);
        model.addAttribute("scheduleId", scheduleId);
        return "teacher/student_list";
    }

    @GetMapping("/class/{scheduleId}/student/{registerId}/review")
    public String showReviewForm(@PathVariable int scheduleId,
                                 @PathVariable long registerId,
                                 HttpSession session,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        Teacher teacher = getCurrentTeacher(session, redirectAttributes);
        if (teacher == null) {
            return "redirect:/login";
        }

        if (!teacherService.isTeacherOfClass(teacher.getName(), scheduleId)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền đánh giá học viên của lớp này");
            return "redirect:/teacher/dashboard";
        }

        ClassRegister classRegister = teacherService.findClassRegisterById(registerId);
        model.addAttribute("classRegister", classRegister);
        model.addAttribute("teacherReviewDTO", new TeacherReviewDTO());
        return "teacher/review_form";
    }

    @PostMapping("/class/student/review")
    public String submitReview(@ModelAttribute TeacherReviewDTO reviewDTO,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Teacher teacher = getCurrentTeacher(session, redirectAttributes);
        if (teacher == null) {
            return "redirect:/login";
        }

        try {
            teacherService.saveReview(reviewDTO, teacher.getName());
            redirectAttributes.addFlashAttribute("success", "Đánh giá học viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/teacher/classes";
    }

    @GetMapping("/class/{scheduleId}/notice")
    public String showNoticeForm(@PathVariable int scheduleId,
                                 HttpSession session,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        Teacher teacher = getCurrentTeacher(session, redirectAttributes);
        if (teacher == null) {
            return "redirect:/login";
        }

        if (!teacherService.isTeacherOfClass(teacher.getName(), scheduleId)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền tạo thông báo cho lớp này");
            return "redirect:/teacher/dashboard";
        }

        model.addAttribute("scheduleId", scheduleId);
        model.addAttribute("teacherNoticeDTO", new TeacherNoticeDTO());
        return "teacher/notice_form";
    }

    @PostMapping("/class/notice")
    public String submitNotice(@ModelAttribute TeacherNoticeDTO noticeDTO,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Teacher teacher = getCurrentTeacher(session, redirectAttributes);
        if (teacher == null) {
            return "redirect:/login";
        }

        try {
            teacherService.saveNotice(noticeDTO, teacher.getName());
            redirectAttributes.addFlashAttribute("success", "Thông báo nghỉ dạy đã được gửi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/teacher/classes";
    }
}
