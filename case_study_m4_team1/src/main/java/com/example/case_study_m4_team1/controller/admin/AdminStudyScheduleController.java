package com.example.case_study_m4_team1.controller.admin;


import com.example.case_study_m4_team1.dto.StudyScheduleAdvancedDTO;
import com.example.case_study_m4_team1.entity.StudySchedule;
import com.example.case_study_m4_team1.entity.Teacher;
import com.example.case_study_m4_team1.enums.ClassStatus;
import com.example.case_study_m4_team1.service.admin.study_schedule.IStudyScheduleServiceAdmin;
import com.example.case_study_m4_team1.service.admin.teacher.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("admin/study_schedules")
public class AdminStudyScheduleController {
    @Autowired
    private IStudyScheduleServiceAdmin studyScheduleServiceAdmin;
    @Autowired
    private ITeacherService teacherService;

    @GetMapping
    public String show(Model model){
        model.addAttribute("studyScheduleList",studyScheduleServiceAdmin.findAll());
        model.addAttribute("teacherList",teacherService.getList());
        return "admin/study_schedule/list";
    }

    @PostMapping("/update_teacher")
    public String updateTeacher(@RequestParam int id,
                                @RequestParam int teacherId,
                                RedirectAttributes redirectAttributes){
        studyScheduleServiceAdmin.setTeacherName(id,teacherId);
        redirectAttributes.addFlashAttribute("mess","Update success !!");
        return "redirect:/admin/study_schedules";
    }

    @PostMapping("/update_price")
    public String updateTeacher(@RequestParam int id,
                                @RequestParam double price,
                                RedirectAttributes redirectAttributes){
        studyScheduleServiceAdmin.setPrice(id,price,ClassStatus.NOT_OPEN);
        redirectAttributes.addFlashAttribute("mess","Update success !!");
        return "redirect:/admin/study_schedules";
    }
}
