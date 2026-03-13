package com.example.case_study_m4_team1.controller.admin;

import com.example.case_study_m4_team1.service.admin.shift.IShiftServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("admin/shifts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminShiftController {
    @Autowired
    private IShiftServiceAdmin shiftServiceAdmin;

    @GetMapping
    public String show(Model model){
        model.addAttribute("shiftList",shiftServiceAdmin.findAll());
        return "admin/shift/list";
    }

}
