package com.example.case_study_m4_team1.repository;

import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClassRegisterRepo extends JpaRepository<ClassRegister,Long> {
    boolean existsByUser_IdAndSchedule_Shift_IdAndStatusRegister(
            long userId,
            int shiftId,
            RegisterStatus statusRegister
    );
}
