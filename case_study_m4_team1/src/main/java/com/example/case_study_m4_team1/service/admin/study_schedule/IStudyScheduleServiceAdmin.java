package com.example.case_study_m4_team1.service.admin.study_schedule;

import com.example.case_study_m4_team1.dto.StudyScheduleAdvancedDTO;
import com.example.case_study_m4_team1.entity.StudySchedule;

import java.time.LocalDate;
import java.util.List;

public interface IStudyScheduleServiceAdmin {
//    List<StudyScheduleAdvancedDTO> getSchedulesByDate(LocalDate date);
    List<StudySchedule> findAll();
}
