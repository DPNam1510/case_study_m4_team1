package com.example.case_study_m4_team1.repository.user;

import com.example.case_study_m4_team1.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByAccountId(Long accountId);
    Users findByAccountUsername(String username);
}
