package com.example.case_study_m4_team1.repository.admin.class_register;

import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IClassRegisterRepoAdmin extends JpaRepository<ClassRegister,Long> {
    @Query("SELECT COUNT(c) FROM ClassRegister c WHERE c.studySchedule.id = :scheduleId AND c.statusRegister = :status")
    int countByScheduleIdAndStatusRegister(@Param("scheduleId") Integer scheduleId,
                                           @Param("status") RegisterStatus status);
}
