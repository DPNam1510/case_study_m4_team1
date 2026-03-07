package com.example.case_study_m4_team1.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherReviewDTO {
    private long classRegisterId;
    private double scores;
    private String review;
}
