package com.example.case_study_m4_team1.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    private long registerId;
    private int payTypeId; // 1: CASH, 2: BANKING
    private String paymentMethod; // "CASH" or "BANKING"
}
