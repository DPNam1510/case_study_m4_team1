package com.example.case_study_m4_team1.repository.admin.study_schedule;

import com.example.case_study_m4_team1.entity.StudySchedule;
import com.example.case_study_m4_team1.enums.ClassStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStudyScheduleRepositoryAdmin extends JpaRepository<StudySchedule,Integer> {
    List<StudySchedule> findByClassNameContaining(String keyword);
    List<StudySchedule> findByStatusClass(ClassStatus classStatus);
}
