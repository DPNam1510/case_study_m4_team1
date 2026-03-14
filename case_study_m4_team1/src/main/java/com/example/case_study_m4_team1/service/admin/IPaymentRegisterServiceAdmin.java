package com.example.case_study_m4_team1.service.admin;

import com.example.case_study_m4_team1.dto.PaymentRegisterDto;
import com.example.case_study_m4_team1.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IPaymentRegisterServiceAdmin {
    Page<PaymentRegisterDto> searchPayment(@Param("searchClassName") String className,
                                           @Param("searchUser") String user,
                                           @Param("searchTeacher") String teacher,
                                           @Param("statusStr") PaymentStatus statusStr,
                                           Pageable pageable);

    Page<PaymentRegisterDto> searchPaymentNotOpen(@Param("searchClassName") String className,
                                                  @Param("searchUser") String user,
                                                  @Param("searchTeacher") String teacher,
                                                  @Param("statusStr") PaymentStatus statusStr,
                                                  Pageable pageable);

    void adminSetPaid(long id);
}
