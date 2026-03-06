package com.example.case_study_m4_team1.repository.user;

import com.example.case_study_m4_team1.entity.PaymentRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPaymentRegisterRepository extends JpaRepository<PaymentRegister, Long> {
    boolean existsByClassRegister_Id(long registerId);
    Optional<PaymentRegister> findByClassRegister_Id(long registerId);
}
