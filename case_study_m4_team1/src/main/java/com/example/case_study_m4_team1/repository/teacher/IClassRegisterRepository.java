package com.example.case_study_m4_team1.repository.teacher;

import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IClassRegisterRepository extends JpaRepository<ClassRegister, Long> {
    List<ClassRegister> findByStudyScheduleIdAndStatusRegister(int scheduleId, RegisterStatus status);
    boolean existsByUserIdAndStudyScheduleId(long userId, int scheduleId);
    List<ClassRegister> findByUserId(long userId);
    @Query("SELECT COUNT(c) FROM ClassRegister c WHERE c.studySchedule.id = :scheduleId AND c.statusRegister = :status")
    int countByStudyScheduleIdAndStatusRegister(@Param("scheduleId") int scheduleId,
                                                @Param("status") RegisterStatus status);
}