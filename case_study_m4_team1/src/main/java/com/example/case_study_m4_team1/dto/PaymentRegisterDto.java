package com.example.case_study_m4_team1.dto;

import java.time.LocalDateTime;

public interface PaymentRegisterDto {
    long getId();
    String getClassName();
    String getUser();
    String getField();
    String getTeacher();
    LocalDateTime getDate();
    double getPrice();
    String getType();
    String getStatus();
}
