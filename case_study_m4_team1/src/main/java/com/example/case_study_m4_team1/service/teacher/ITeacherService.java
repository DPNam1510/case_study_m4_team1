package com.example.case_study_m4_team1.service.teacher;

import com.example.case_study_m4_team1.dto.teacher.TeacherNoticeDTO;
import com.example.case_study_m4_team1.dto.teacher.TeacherReviewDTO;
import com.example.case_study_m4_team1.entity.ClassRegister;
import com.example.case_study_m4_team1.entity.StudySchedule;
import com.example.case_study_m4_team1.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface ITeacherService {
    Teacher findById(int id);
    Teacher findByAccountUsername(String username);

    List<StudySchedule> getTeacherSchedules(int teacherId);

    List<ClassRegister> getStudentsInClass(int scheduleId);

    ClassRegister findClassRegisterById(long id);

    void saveReview(TeacherReviewDTO reviewDTO);

    void saveNotice(TeacherNoticeDTO noticeDTO);
}
