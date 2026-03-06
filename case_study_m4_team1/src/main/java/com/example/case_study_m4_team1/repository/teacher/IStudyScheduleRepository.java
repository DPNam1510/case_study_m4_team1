package com.example.case_study_m4_team1.repository.teacher;

import com.example.case_study_m4_team1.entity.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStudyScheduleRepository extends JpaRepository<StudySchedule, Integer> {
    List<StudySchedule> findByTeacherId(int teacherId);
}
