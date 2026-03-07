package com.example.case_study_m4_team1.repository.teacher;

import com.example.case_study_m4_team1.entity.TeacherNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITeacherNoticeRepository extends JpaRepository<TeacherNotice, Long> {
    List<TeacherNotice> findByClassRegister_UserId(long userId);
}
