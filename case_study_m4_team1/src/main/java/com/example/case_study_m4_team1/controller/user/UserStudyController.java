package com.example.case_study_m4_team1.controller.user;

import com.example.case_study_m4_team1.dto.user.PaymentRequestDTO;
import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.entity.StudySchedule;
import com.example.case_study_m4_team1.entity.TeacherNotice;
import com.example.case_study_m4_team1.entity.TeacherReview;
import com.example.case_study_m4_team1.service.user.IUserStudyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/study")
@PreAuthorize("hasRole('USER')")
public class UserStudyController {

    @Autowired
    private IUserStudyService userStudyService;


    @GetMapping("/classes")
    public String viewAvailableClasses(Model model) {
        List<StudySchedule> openClasses = userStudyService.getOpenClasses();
        model.addAttribute("classes", openClasses);
        return "user/class_list";
    }

    @GetMapping("/class/{scheduleId}/detail")
    public String viewClassDetail(@PathVariable int scheduleId, Model model) {
        StudySchedule schedule = userStudyService.findScheduleById(scheduleId);
        int registeredCount = userStudyService.countRegisteredStudents(scheduleId);

        model.addAttribute("schedule", schedule);
        model.addAttribute("registeredCount", registeredCount);
        model.addAttribute("availableSlots", schedule.getMaxStudents() - registeredCount);
        return "user/class_detail";
    }

    @PostMapping("/register")
    public String registerClass(@RequestParam int scheduleId,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {

        String username = principal.getName();

        Long registerId = null;
        try {
            registerId = userStudyService.registerClass(username, scheduleId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        redirectAttributes.addFlashAttribute("success", "Register class success!");
        return "redirect:/user/study/payment/" + registerId;
    }

    @GetMapping("/my-schedule")
    public String viewMySchedule(Principal principal, Model model) {
        String username = principal.getName();

        List<ClassRegister> myClasses = userStudyService.getUserClasses(username);
        model.addAttribute("myClasses", myClasses);
        return "user/my_schedule";
    }

    @GetMapping("/notices")
    public String viewNotices(Principal principal, Model model) {
        String username = principal.getName();

        List<TeacherNotice> notices = userStudyService.getUserNotices(username);
        model.addAttribute("notices", notices);
        return "user/notice_list";
    }

    @GetMapping("/reviews")
    public String viewMyReviews(Principal principal, Model model) {
        String username = principal.getName();

        List<TeacherReview> reviews = userStudyService.getUserReviews(username);
        model.addAttribute("reviews", reviews);
        return "user/review_list";
    }

    @GetMapping("/payment/{registerId}")
    public String showPaymentForm(@PathVariable long registerId,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        String username = principal.getName();
        ClassRegister register = userStudyService.findClassRegisterById(registerId);

        // Kiểm tra quyền sở hữu
        if (!userStudyService.isOwner(username, registerId)) {
            redirectAttributes.addFlashAttribute("error", "You do not have access !!");
            return "redirect:/user/study/my-schedule";
        }

        model.addAttribute("register", register);
        model.addAttribute("paymentRequest", new PaymentRequestDTO());
        return "user/payment_form";
    }

    @PostMapping("/payment/process")
    public String processPayment(@ModelAttribute PaymentRequestDTO paymentRequest,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        try {
            String username = principal.getName();

            userStudyService.processPayment(paymentRequest, username);
            redirectAttributes.addFlashAttribute("success", "Payment success!");
            return "redirect:/user/study/my-schedule";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Payment fails: " + e.getMessage());
            return "redirect:/user/study/payment/" + paymentRequest.getRegisterId();
        }
    }
}
