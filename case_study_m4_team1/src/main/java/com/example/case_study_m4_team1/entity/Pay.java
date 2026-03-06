package com.example.case_study_m4_team1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "pay")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "pay_type", unique = true, nullable = false)
    private String payType;

    @OneToMany(mappedBy = "pay")
    private List<PaymentRegister> paymentRegisters;

    @OneToMany(mappedBy = "pay")
    private List<PaymentFieldBook> paymentFieldBooks;
}
