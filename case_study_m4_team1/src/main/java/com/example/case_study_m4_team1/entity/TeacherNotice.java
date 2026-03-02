package com.example.case_study_m4_team1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teacher_notice")
@Getter
@Setter
@NoArgsConstructor
public class TeacherNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "class_register_id")
    private ClassRegister classRegister;
}
