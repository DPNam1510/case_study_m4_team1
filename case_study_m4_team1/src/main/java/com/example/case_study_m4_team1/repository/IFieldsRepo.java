package com.example.case_study_m4_team1.repository;

import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.enums.FieldStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFieldsRepo extends JpaRepository<Fields,Integer> {

    // check tình trạng sân
    boolean existsByIdAndStatus(Integer id, FieldStatus status);
}
