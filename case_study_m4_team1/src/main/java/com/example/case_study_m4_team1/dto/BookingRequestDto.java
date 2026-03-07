package com.example.case_study_m4_team1.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    @NotNull
    private int fieldId;
    @NotNull
    private int shiftId;
    @NotNull
    @FutureOrPresent(message = "Date must be today or future")
    private LocalDate dateBook;
}
