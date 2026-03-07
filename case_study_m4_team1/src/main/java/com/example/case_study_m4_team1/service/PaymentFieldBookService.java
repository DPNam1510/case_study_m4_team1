package com.example.case_study_m4_team1.service;

import com.example.case_study_m4_team1.dto.PaymentFieldBookDto;
import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.entity.Pay;
import com.example.case_study_m4_team1.entity.PaymentFieldBook;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.enums.PaymentStatus;
import com.example.case_study_m4_team1.exception.BookingException;
import com.example.case_study_m4_team1.exception.NotFoundException;
import com.example.case_study_m4_team1.repository.IPayRepository;
import com.example.case_study_m4_team1.repository.IPaymentFieldBookRepository;
import com.example.case_study_m4_team1.repository.admin.field_book.IFieldBookRepositoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PaymentFieldBookService implements IPaymentFieldBookService{

    @Autowired
    private IPaymentFieldBookRepository paymentFieldBookRepository;
    @Autowired
    private IFieldBookRepositoryAdmin fieldBookRepositoryAdmin;

    @Autowired
    private IPayRepository payRepo;

    @Override
    public Page<PaymentFieldBookDto> searchPayment (String user, String field, PaymentStatus status, Pageable pageable) {
        return paymentFieldBookRepository.searchPayment(
                "%" + user + "%",
                "%" + field + "%",
                status == null ? null : status.name(),
                pageable
        );
    }

    @Override
    public void createPayment(long fieldBookId, int payId) {
        FieldBook booking = fieldBookRepositoryAdmin.findById(fieldBookId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy booking"));

        if(paymentFieldBookRepository.existsPayment(fieldBookId) > 0){
            throw new BookingException("Booking đã có payment");
        }

        if (booking.getStatus() != BookingStatus.APPROVED) {
            throw new BookingException("Booking phải được APPROVED trước khi payment");
        }

        Pay pay = payRepo.findById(payId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hình thức thanh toán"));

        PaymentFieldBook payment = new PaymentFieldBook();
        payment.setFieldBook(booking);
        payment.setPay(pay);
        payment.setStatus(PaymentStatus.UNPAID);
        paymentFieldBookRepository.save(payment);
    }

    public void adminSetPaid(long paymentId) {
        PaymentFieldBook payment = paymentFieldBookRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy payment"));

        if(payment.getStatus() == PaymentStatus.PAID){
            throw new BookingException("Payment đã được PAID trước đó!");
        }

        payment.setStatus(PaymentStatus.PAID);
        paymentFieldBookRepository.save(payment);
    }

    @Override
    public List<PaymentFieldBookDto> findByUserId(long userId) {
        return paymentFieldBookRepository.findByUserId(userId);
    }
}
