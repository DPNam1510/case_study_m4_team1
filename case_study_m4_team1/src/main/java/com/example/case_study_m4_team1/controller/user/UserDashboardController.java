package com.example.case_study_m4_team1.controller.user;

import com.example.case_study_m4_team1.service.user.IUserStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserDashboardController {
    @Autowired
    private IUserStudyService userStudyService;

    @GetMapping
    public String home(Model model, Principal principal) {
        String username = principal.getName();
        Long userId = userStudyService.getUserIdByUsername(username);
        model.addAttribute("userId", userId);
        return "user/dashboard_user";
    }
}
