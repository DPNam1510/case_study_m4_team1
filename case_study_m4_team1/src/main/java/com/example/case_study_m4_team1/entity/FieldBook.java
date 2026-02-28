package com.example.case_study_m4_team1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "field_book", uniqueConstraints = @UniqueConstraint(
        columnNames = {"field_id", "shift_id", "date_book"}))
public class FieldBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private int fieldId;
    private int shiftId;
    private LocalDate dateBook;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
