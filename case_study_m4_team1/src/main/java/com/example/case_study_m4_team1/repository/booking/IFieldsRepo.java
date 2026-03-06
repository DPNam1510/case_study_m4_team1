package com.example.case_study_m4_team1.repository.booking;

import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.enums.FieldStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFieldsRepo extends JpaRepository<Fields, Integer> {
    List<Fields> findAllByOrderByNameAsc();
}
