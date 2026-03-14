package com.example.case_study_m4_team1.repository.role;

import com.example.case_study_m4_team1.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IRoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}