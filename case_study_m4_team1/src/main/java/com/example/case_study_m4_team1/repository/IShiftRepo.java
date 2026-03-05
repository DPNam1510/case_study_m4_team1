package com.example.case_study_m4_team1.repository;

import com.example.case_study_m4_team1.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShiftRepo extends JpaRepository<Shift,Integer> {
}
