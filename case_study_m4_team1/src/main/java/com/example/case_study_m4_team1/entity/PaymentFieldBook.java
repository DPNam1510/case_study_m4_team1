package com.example.case_study_m4_team1.entity;

import com.example.case_study_m4_team1.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_field_book")
@Getter
@Setter
@NoArgsConstructor
public class PaymentFieldBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "pay_id")
    private Pay pay;

    @OneToOne
    @JoinColumn(name = "field_book_id", unique = true)
    private FieldBook fieldBook;
}
