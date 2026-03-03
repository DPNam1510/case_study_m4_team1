package com.example.case_study_m4_team1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teacher_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double scores;

    private String review;

    @OneToOne
    @JoinColumn(name = "class_register_id", unique = true)
    private ClassRegister classRegister;
}
