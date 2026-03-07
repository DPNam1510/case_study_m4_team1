package com.example.case_study_m4_team1.dto;

import com.example.case_study_m4_team1.enums.ClassStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleAdvancedDTO {
    private int scheduleId;
    private String className;
    private int fieldId;
    private String fieldName;
    private String teacherName;
    private String startTime;
    private String endTime;
    private ClassStatus statusClass;
    private int registeredStudents;
    private int minStudents;
    private int maxStudents;
}