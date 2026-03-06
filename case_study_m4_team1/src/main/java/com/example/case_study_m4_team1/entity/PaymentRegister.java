package com.example.case_study_m4_team1.entity;

import com.example.case_study_m4_team1.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_register")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "pay_id")
    private Pay pay;

    @OneToOne
    @JoinColumn(name = "register_id", unique = true)
    private ClassRegister classRegister;
}
