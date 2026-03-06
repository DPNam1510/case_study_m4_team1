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
    private int fieldId;
    private int shiftId;
    private String fieldName;
    private String shiftTime;
    private LocalDate dateBook;
    private BookingStatus status;
}
