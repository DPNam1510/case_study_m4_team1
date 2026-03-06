package com.example.case_study_m4_team1.repository.user;

import com.example.case_study_m4_team1.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPayRepository extends JpaRepository<Pay, Integer> {
}
