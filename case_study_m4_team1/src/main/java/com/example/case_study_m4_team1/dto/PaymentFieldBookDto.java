package com.example.case_study_m4_team1.dto;


import java.time.LocalDate;


public interface PaymentFieldBookDto {
    long getId();
    String getUser();
    String getField();
    LocalDate getDate();
    double getPrice();
    String getType();
    String getStatus();

}
