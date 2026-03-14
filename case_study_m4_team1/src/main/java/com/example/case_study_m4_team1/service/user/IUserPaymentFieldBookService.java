package com.example.case_study_m4_team1.service.user;

import com.example.case_study_m4_team1.dto.BookingResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserPaymentFieldBookService {
    Page<BookingResponseDto> getApprovedBookingsForPayment(Long userId, Pageable pageable);
    void createPayment(Long bookingId, int payTypeId, String username) throws Exception;
}
