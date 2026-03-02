package com.example.case_study_m4_team1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private Account account;

    @OneToMany(mappedBy = "user")
    private List<FieldBook> fieldBooks;

    @OneToMany(mappedBy = "user")
    private List<ClassRegister> classRegisters;
}
