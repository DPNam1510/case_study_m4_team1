package com.example.case_study_m4_team1.repository.booking;

import com.example.case_study_m4_team1.entity.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudyScheduleRepo extends JpaRepository<StudySchedule,Integer> {
}
