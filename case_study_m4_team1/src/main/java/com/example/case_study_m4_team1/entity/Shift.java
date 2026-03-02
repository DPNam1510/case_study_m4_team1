package com.example.case_study_m4_team1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "shift")
@Getter
@Setter
@NoArgsConstructor
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;

    @OneToMany(mappedBy = "shift")
    private List<FieldBook> fieldBooks;

    @OneToMany(mappedBy = "shift")
    private List<StudySchedule> schedules;
}
