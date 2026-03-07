package com.example.case_study_m4_team1.repository.teacher;

import com.example.case_study_m4_team1.entity.TeacherReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITeacherReviewRepository extends JpaRepository<TeacherReview, Long> {
    boolean existsByClassRegisterId(long classRegisterId);
    Optional<TeacherReview> findByClassRegisterId(long classRegisterId);
}
