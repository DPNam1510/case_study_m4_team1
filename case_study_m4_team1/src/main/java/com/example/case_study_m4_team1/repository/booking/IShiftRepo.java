package com.example.case_study_m4_team1.repository.booking;

import com.example.case_study_m4_team1.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShiftRepo extends JpaRepository<Shift,Integer> {
    List<Shift> findAllByOrderByStartTimeAsc();
}
