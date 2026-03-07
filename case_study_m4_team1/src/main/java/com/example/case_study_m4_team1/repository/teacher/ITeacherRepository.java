package com.example.case_study_m4_team1.repository.teacher;


import com.example.case_study_m4_team1.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByAccountId(Long accountId);
}
