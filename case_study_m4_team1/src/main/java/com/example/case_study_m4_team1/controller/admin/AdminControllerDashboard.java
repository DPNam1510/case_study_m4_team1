package com.example.case_study_m4_team1.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControllerDashboard {
    @GetMapping
    public String adminDashboard(){
        return "admin/dashboard_admin";
    }
}
