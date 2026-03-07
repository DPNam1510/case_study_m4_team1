package com.example.case_study_m4_team1.service.admin.study_schedule;

import com.example.case_study_m4_team1.dto.StudyScheduleAdvancedDTO;
import com.example.case_study_m4_team1.entity.StudySchedule;
import com.example.case_study_m4_team1.entity.Teacher;
import com.example.case_study_m4_team1.enums.ClassStatus;

import java.time.LocalDate;
import java.util.List;

public interface IStudyScheduleServiceAdmin {
//    List<StudyScheduleAdvancedDTO> getSchedulesByDate(LocalDate date);
    List<StudySchedule> findAll();

    StudySchedule findById(int id);
    void setTeacherName(int id, int teacherId);
    void setPrice(int id, double price, ClassStatus status);
}
