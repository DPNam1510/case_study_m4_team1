package com.example.case_study_m4_team1.service.teacher;

import com.example.case_study_m4_team1.dto.teacher.TeacherNoticeDTO;
import com.example.case_study_m4_team1.dto.teacher.TeacherReviewDTO;
import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.entity.StudySchedule;
import com.example.case_study_m4_team1.entity.Teacher;

import java.util.List;

public interface ITeacherService {
    Teacher findByUsername(String username);
    List<StudySchedule> getTeacherSchedules(int teacherId);
    List<ClassRegister> getStudentsInClass(int scheduleId);
    boolean isTeacherOfClass(String username, int scheduleId);
    ClassRegister findClassRegisterById(long id);
    void saveReview(TeacherReviewDTO reviewDTO, String username);
    void saveNotice(TeacherNoticeDTO noticeDTO, String username);
}
