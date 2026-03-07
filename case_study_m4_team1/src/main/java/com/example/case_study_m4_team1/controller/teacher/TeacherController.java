package com.example.case_study_m4_team1.controller.teacher;

import com.example.case_study_m4_team1.dto.teacher.TeacherNoticeDTO;
import com.example.case_study_m4_team1.dto.teacher.TeacherReviewDTO;
import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.entity.StudySchedule;
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

    // tạm dùng teacherId cố định vì đã bỏ login
    private final int teacherId = 1;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        Teacher teacher = teacherService.findById(teacherId);

        model.addAttribute("teacher", teacher);

        return "teacher/dashboard";
    }

    @GetMapping("/classes")
    public String viewMyClasses(Model model) {

        List<StudySchedule> schedules = teacherService.getTeacherSchedules(teacherId);

        model.addAttribute("schedules", schedules);

        return "teacher/class_list";
    }

    @GetMapping("/class/{scheduleId}/students")
    public String viewStudentsInClass(@PathVariable int scheduleId,
                                      Model model) {

        List<ClassRegister> students = teacherService.getStudentsInClass(scheduleId);

        model.addAttribute("students", students);
        model.addAttribute("scheduleId", scheduleId);

        return "teacher/student_list";
    }

    @GetMapping("/class/{scheduleId}/student/{registerId}/review")
    public String showReviewForm(@PathVariable int scheduleId,
                                 @PathVariable long registerId,
                                 Model model) {

        ClassRegister classRegister = teacherService.findClassRegisterById(registerId);

        model.addAttribute("classRegister", classRegister);
        model.addAttribute("teacherReviewDTO", new TeacherReviewDTO());
        model.addAttribute("scheduleId", scheduleId);

        return "teacher/review_form";
    }

    @PostMapping("/class/student/review")
    public String submitReview(@ModelAttribute TeacherReviewDTO reviewDTO,
                               RedirectAttributes redirectAttributes) {

        try {
            teacherService.saveReview(reviewDTO);
            redirectAttributes.addFlashAttribute("success", "Đánh giá học viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }

        return "redirect:/teacher/classes";
    }

    @GetMapping("/class/{scheduleId}/notice")
    public String showNoticeForm(@PathVariable int scheduleId,
                                 Model model) {

        model.addAttribute("scheduleId", scheduleId);
        model.addAttribute("teacherNoticeDTO", new TeacherNoticeDTO());

        return "teacher/notice_form";
    }

    @PostMapping("/class/notice")
    public String submitNotice(@ModelAttribute TeacherNoticeDTO noticeDTO,
                               RedirectAttributes redirectAttributes) {

        try {
            teacherService.saveNotice(noticeDTO);
            redirectAttributes.addFlashAttribute("success", "Thông báo nghỉ dạy đã được gửi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }

        return "redirect:/teacher/classes";
    }
}