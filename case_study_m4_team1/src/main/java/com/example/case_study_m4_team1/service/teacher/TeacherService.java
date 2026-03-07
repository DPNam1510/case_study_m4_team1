package com.example.case_study_m4_team1.service.teacher;

import com.example.case_study_m4_team1.dto.teacher.TeacherNoticeDTO;
import com.example.case_study_m4_team1.dto.teacher.TeacherReviewDTO;
import com.example.case_study_m4_team1.entity.*;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import com.example.case_study_m4_team1.repository.teacher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherService implements ITeacherService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ITeacherRepository teacherRepository;

    @Autowired
    private IStudyScheduleRepository studyScheduleRepository;

    @Autowired
    private IClassRegisterRepository classRegisterRepository;

    @Autowired
    private ITeacherReviewRepository teacherReviewRepository;

    @Autowired
    private ITeacherNoticeRepository teacherNoticeRepository;

    @Override
    public Teacher findByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return teacherRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    @Override
    public List<StudySchedule> getTeacherSchedules(int teacherId) {
        return studyScheduleRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<ClassRegister> getStudentsInClass(int scheduleId) {
        return classRegisterRepository.findByStudyScheduleIdAndStatusRegister(
                scheduleId, RegisterStatus.APPROVED);
    }

    @Override
    public boolean isTeacherOfClass(String username, int scheduleId) {
        Teacher teacher = findByUsername(username);
        StudySchedule schedule = studyScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return schedule.getTeacher().getId() == teacher.getId();
    }

    @Override
    public ClassRegister findClassRegisterById(long id) {
        return classRegisterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class register not found"));
    }

    @Override
    public void saveReview(TeacherReviewDTO reviewDTO, String username) {
        ClassRegister classRegister = classRegisterRepository.findById(reviewDTO.getClassRegisterId())
                .orElseThrow(() -> new RuntimeException("Class register not found"));

        // Kiểm tra quyền
        if (!isTeacherOfClass(username, classRegister.getStudySchedule().getId())) {
            throw new RuntimeException("You are not the teacher of this class");
        }

        // Kiểm tra đã review chưa
        if (teacherReviewRepository.existsByClassRegisterId(reviewDTO.getClassRegisterId())) {
            throw new RuntimeException("This student has already been reviewed");
        }

        TeacherReview review = new TeacherReview();
        review.setClassRegister(classRegister);
        review.setScores(reviewDTO.getScores());
        review.setReview(reviewDTO.getReview());

        teacherReviewRepository.save(review);
    }

    @Override
    public void saveNotice(TeacherNoticeDTO noticeDTO, String username) {
        StudySchedule schedule = studyScheduleRepository.findById(noticeDTO.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Kiểm tra quyền
        if (!isTeacherOfClass(username, noticeDTO.getScheduleId())) {
            throw new RuntimeException("You are not the teacher of this class");
        }

        // Lấy tất cả học viên đã đăng ký lớp này
        List<ClassRegister> registers = classRegisterRepository
                .findByStudyScheduleIdAndStatusRegister(noticeDTO.getScheduleId(), RegisterStatus.APPROVED);

        for (ClassRegister register : registers) {
            TeacherNotice notice = new TeacherNotice();
            notice.setClassRegister(register);
            notice.setReason(noticeDTO.getReason());
            teacherNoticeRepository.save(notice);
        }
    }
}
