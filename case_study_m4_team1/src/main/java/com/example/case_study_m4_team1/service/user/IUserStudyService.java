package com.example.case_study_m4_team1.service.user;

import com.example.case_study_m4_team1.dto.user.PaymentRequestDTO;
import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.entity.StudySchedule;
import com.example.case_study_m4_team1.entity.TeacherNotice;
import com.example.case_study_m4_team1.entity.TeacherReview;

import java.util.List;

public interface IUserStudyService {
    List<StudySchedule> getOpenClasses();
    StudySchedule findScheduleById(int scheduleId);
    int countRegisteredStudents(int scheduleId);
    Long registerClass(String username, int scheduleId) throws Exception;
    Long getUserIdByUsername(String username);
    List<ClassRegister> getUserClasses(String username);
    List<TeacherNotice> getUserNotices(String username);
    List<TeacherReview> getUserReviews(String username);
    ClassRegister findClassRegisterById(long id);
    boolean isOwner(String username, long registerId);
    void processPayment(PaymentRequestDTO paymentRequest, String username) throws Exception;
}
