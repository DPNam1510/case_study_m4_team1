package com.example.case_study_m4_team1.dto;

import com.example.case_study_m4_team1.enums.BookingStatus;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;
    private String fieldName;
    private LocalDate dateBook;
    private String shiftTime;
    private BookingStatus status;
}
