package com.example.case_study_m4_team1.repository.admin.teacher;

import com.example.case_study_m4_team1.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeacherRepo extends JpaRepository<Teacher,Integer> {
}
