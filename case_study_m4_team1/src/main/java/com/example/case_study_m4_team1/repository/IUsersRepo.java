package com.example.case_study_m4_team1.repository;

import com.example.case_study_m4_team1.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepo extends JpaRepository<Users,Long> {
}
