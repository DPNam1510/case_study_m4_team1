package com.example.case_study_m4_team1.entity;

import com.example.case_study_m4_team1.enums.FieldStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "fields")
@Getter
@Setter
@NoArgsConstructor
public class Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    private Double price;

    @Enumerated(EnumType.STRING)
    private FieldStatus status;

    @OneToMany(mappedBy = "field")
    private List<FieldBook> fieldBooks;

    @OneToMany(mappedBy = "field")
    private List<StudySchedule> schedules;
}
