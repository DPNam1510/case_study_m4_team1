package com.example.case_study_m4_team1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDto {
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
}
