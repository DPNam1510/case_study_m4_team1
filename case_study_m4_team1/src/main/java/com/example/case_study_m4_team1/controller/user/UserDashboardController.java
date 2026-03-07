package com.example.case_study_m4_team1.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserDashboardController {
    @GetMapping
    public String home(){
        return "user/dashboard_user";
    }
}
