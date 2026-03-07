package com.example.case_study_m4_team1.service.admin.study_schedule;

import com.example.case_study_m4_team1.dto.StudyScheduleAdvancedDTO;
import com.example.case_study_m4_team1.entity.StudySchedule;
import com.example.case_study_m4_team1.entity.Teacher;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.enums.ClassStatus;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import com.example.case_study_m4_team1.exception.BookingException;
import com.example.case_study_m4_team1.exception.NotFoundException;
import com.example.case_study_m4_team1.repository.admin.class_register.IClassRegisterRepoAdmin;
import com.example.case_study_m4_team1.repository.admin.study_schedule.IStudyScheduleRepositoryAdmin;
import com.example.case_study_m4_team1.repository.admin.teacher.ITeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudyScheduleServiceAdmin implements IStudyScheduleServiceAdmin {
    @Autowired
    private IStudyScheduleRepositoryAdmin studyScheduleRepositoryAdmin;
    @Autowired
    private ITeacherRepo teacherRepo;
    @Autowired
    private IClassRegisterRepoAdmin classRegisterRepoAdmin;

    @Override
    public List<StudySchedule> findAll() {
        return studyScheduleRepositoryAdmin.findAll();
    }

    @Override
    public StudySchedule findById(int id) {
        return studyScheduleRepositoryAdmin.findById(id).orElse(null);
    }

    @Override
    public void setTeacherName(int id, int teacherId) {
        StudySchedule studySchedule = studyScheduleRepositoryAdmin.findById(id).orElse(null);
        Teacher teacher = teacherRepo.findById(teacherId).orElse(null);
        if (studySchedule==null){
            throw new NotFoundException("Không tìm thấy mã lịch học");
        }
        studySchedule.setTeacher(teacher);
        studyScheduleRepositoryAdmin.save(studySchedule);
    }

    @Override
    public void setPrice(int id, double price, ClassStatus status) {
        StudySchedule studySchedule = studyScheduleRepositoryAdmin.findById(id).orElse(null);
        if (status == ClassStatus.OPEN){
            throw new BookingException("Lịch học đã mở, không thể thay đổi giá");
        }
        if (price<500000){
            throw new RuntimeException("Đơn giá quá thấp, vui lòng liên hệ chủ sở hửu");
        }
        studySchedule.setPrice(price);
        studyScheduleRepositoryAdmin.save(studySchedule);
    }

//    @Override
    ////    public List<StudyScheduleAdvancedDTO> getSchedulesByDate(LocalDate date) {
    ////        DayOfWeek dayOfWeek = date.getDayOfWeek();
    ////        List<StudySchedule> schedules;
    ////
    ////        if (dayOfWeek == DayOfWeek.MONDAY
    ////                || dayOfWeek == DayOfWeek.WEDNESDAY
    ////                || dayOfWeek == DayOfWeek.FRIDAY) {
    ////
    ////            schedules = studyScheduleRepositoryAdmin
    ////                    .findByClassNameContaining("2-4-6");
    ////
    ////        } else if (dayOfWeek == DayOfWeek.TUESDAY
    ////                || dayOfWeek == DayOfWeek.THURSDAY
    ////                || dayOfWeek == DayOfWeek.SATURDAY) {
    ////
    ////            schedules = studyScheduleRepositoryAdmin
    ////                    .findByClassNameContaining("3-5-7");
    ////
    ////        } else {
    ////            return new ArrayList<>();
    ////        }
    ////
    ////        List<StudyScheduleAdvancedDTO> result = new ArrayList<>();
    ////
    ////        for (StudySchedule s : schedules) {
    ////            int registered = classRegisterRepoAdmin
    ////                    .countByScheduleIdAndStatusRegister(
    ////                            s.getId(),
    ////                            RegisterStatus.APPROVED
    ////                    );
    ////
    ////            ClassStatus status;
    ////
    ////            if (registered < s.getMinStudents()) {
    ////                status = ClassStatus.NOT_OPEN;
    ////            } else if (registered < s.getMaxStudents()) {
    ////                status = ClassStatus.OPEN;
    ////            } else {
    ////                status = ClassStatus.FULL;
    ////            }
    ////
    ////            result.add(new StudyScheduleAdvancedDTO(
    ////                    s.getId(),
    ////                    s.getClassName(),
    ////                    s.getField().getId(),
    ////                    s.getField().getName(),
    ////                    s.getTeacher().getName(),
    ////                    s.getShift().getStartTime().toString(),
    ////                    s.getShift().getEndTime().toString(),
    ////                    status,
    ////                    registered,
    ////                    s.getMinStudents(),
    ////                    s.getMaxStudents()
    ////            ));
    ////        }
    ////        return result;
    ////    }

}
