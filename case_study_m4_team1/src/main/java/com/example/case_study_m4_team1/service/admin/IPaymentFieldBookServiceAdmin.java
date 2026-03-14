package com.example.case_study_m4_team1.service.admin;

import com.example.case_study_m4_team1.dto.PaymentFieldBookDto;
import com.example.case_study_m4_team1.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IPaymentFieldBookServiceAdmin {

    Page<PaymentFieldBookDto> searchPayment(
            String user,
            String field,
            PaymentStatus status,
            Pageable pageable
    );

    void createPayment(long fieldBookId, int payId);

    void adminSetPaid(long id);

    boolean existsByBookingId(long bookingId);

    List<PaymentFieldBookDto> findByUserId(@Param("userId") long userId);
}
