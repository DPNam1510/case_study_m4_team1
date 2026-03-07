package com.example.case_study_m4_team1.service.admin.teacher;

import com.example.case_study_m4_team1.entity.Teacher;
import com.example.case_study_m4_team1.repository.admin.teacher.ITeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceAdmin implements ITeacherServiceAdmin {

    @Autowired
    private ITeacherRepo teacherRepo;

    @Override
    public List<Teacher> getList() {
        return teacherRepo.findAll();
    }
}
