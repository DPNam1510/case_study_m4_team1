package com.example.case_study_m4_team1.entity;

import com.example.case_study_m4_team1.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "field_book", uniqueConstraints = @UniqueConstraint(
        columnNames = {"field_id", "shift_id", "date_book"}))
@Getter
@Setter
@NoArgsConstructor
public class FieldBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Fields field;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    private LocalDate dateBook;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToOne(mappedBy = "fieldBook")
    private PaymentFieldBook payment;

}
