package com.example.case_study_m4_team1.entity;

import com.example.case_study_m4_team1.enums.ClassStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "study_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String className;

    private Double price;

    private int minStudents;
    private int maxStudents;

    @Enumerated(EnumType.STRING)
    private ClassStatus statusClass;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Fields field;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @OneToMany(mappedBy = "schedule")
    private List<ClassRegister> registers;
}
