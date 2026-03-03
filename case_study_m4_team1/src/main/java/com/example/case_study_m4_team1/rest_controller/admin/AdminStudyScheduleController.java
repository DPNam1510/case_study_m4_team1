package com.example.case_study_m4_team1.rest_controller.admin;


import com.example.case_study_m4_team1.dto.StudyScheduleAdvancedDTO;
import com.example.case_study_m4_team1.service.admin.study_schedule.IStudyScheduleServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("admin/study_schedules")
public class AdminStudyScheduleController {
    @Autowired
    private IStudyScheduleServiceAdmin studyScheduleServiceAdmin;

    @GetMapping
    public ResponseEntity<List<StudyScheduleAdvancedDTO>> getSchedules(
            @RequestParam String date
    ) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(studyScheduleServiceAdmin.getSchedulesByDate(localDate));
    }
}
