package com.example.case_study_m4_team1.repository.booking;

import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassRegisterRepo extends JpaRepository<ClassRegister,Long> {
    boolean existsByUser_IdAndStudySchedule_Shift_IdAndStatusRegister(
            long userId,
            int shiftId,
            RegisterStatus statusRegister
    );
}
