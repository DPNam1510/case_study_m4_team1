package com.example.case_study_m4_team1.repository.teacher;

import com.example.case_study_m4_team1.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
