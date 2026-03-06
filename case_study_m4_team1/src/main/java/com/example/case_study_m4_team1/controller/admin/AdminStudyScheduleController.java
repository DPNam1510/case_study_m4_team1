package com.example.case_study_m4_team1.controller.admin;


import com.example.case_study_m4_team1.dto.StudyScheduleAdvancedDTO;
import com.example.case_study_m4_team1.service.admin.study_schedule.IStudyScheduleServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("admin/study_schedules")
public class AdminStudyScheduleController {
    @Autowired
    private IStudyScheduleServiceAdmin studyScheduleServiceAdmin;

    @GetMapping
    public String show(Model model){
        model.addAttribute("studyScheduleList",studyScheduleServiceAdmin.findAll());
        return "admin/study_schedule/list";
    }
}
