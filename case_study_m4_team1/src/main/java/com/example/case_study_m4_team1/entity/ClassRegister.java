package com.example.case_study_m4_team1.entity;

import com.example.case_study_m4_team1.enums.RegisterStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "class_register")
@Getter
@Setter
@NoArgsConstructor
public class ClassRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateRegister;

    @Enumerated(EnumType.STRING)
    private RegisterStatus statusRegister;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private StudySchedule schedule;

    @OneToOne(mappedBy = "classRegister")
    private TeacherReview review;

    @OneToOne(mappedBy = "classRegister")
    private PaymentRegister payment;
}
