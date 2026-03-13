package com.example.case_study_m4_team1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController403 {

    @GetMapping("/403")
    public String accessDenied() {
        return "/403";
    }
}