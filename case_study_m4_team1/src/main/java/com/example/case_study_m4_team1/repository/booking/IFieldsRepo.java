package com.example.case_study_m4_team1.repository.booking;

import com.example.case_study_m4_team1.entity.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFieldsRepo extends JpaRepository<Fields, Integer> {
    List<Fields> findAllByOrderByNameAsc();
}
