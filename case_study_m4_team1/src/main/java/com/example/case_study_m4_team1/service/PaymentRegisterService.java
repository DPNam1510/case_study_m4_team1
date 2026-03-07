package com.example.case_study_m4_team1.service;

import com.example.case_study_m4_team1.dto.PaymentRegisterDto;
import com.example.case_study_m4_team1.entity.PaymentRegister;
import com.example.case_study_m4_team1.enums.PaymentStatus;
import com.example.case_study_m4_team1.exception.BookingException;
import com.example.case_study_m4_team1.repository.IPaymentRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentRegisterService implements IPaymentRegisterService{

    @Autowired
    private IPaymentRegisterRepository paymentRegisterRepository;

    @Override
    public Page<PaymentRegisterDto> searchPayment(String className,
                                                  String user,
                                                  String teacher,
                                                  PaymentStatus statusStr, Pageable pageable) {
        return paymentRegisterRepository.searchPayment(
                "%" + className + "%",
                "%" + user + "%",
                "%" + teacher + "%",
                statusStr == null ? null : statusStr.name(),
                pageable
        );
    }

    @Override
    public void adminSetPaid(long id) {
        PaymentRegister paymentRegister = paymentRegisterRepository.findById(id).orElse(null);

        if(paymentRegister.getStatus() == PaymentStatus.PAID){
            throw new BookingException("Payment đã được PAID trước đó!");
        }

        paymentRegister.setStatus(PaymentStatus.PAID);
        paymentRegisterRepository.save(paymentRegister);

    }
}
