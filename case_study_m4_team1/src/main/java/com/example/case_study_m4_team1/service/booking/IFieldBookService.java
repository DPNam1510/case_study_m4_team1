package com.example.case_study_m4_team1.service.booking;

import com.example.case_study_m4_team1.dto.BookingRequestDto;
import com.example.case_study_m4_team1.dto.BookingResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFieldBookService {
    BookingResponseDto createBooking(Long userId, BookingRequestDto request);

    BookingResponseDto updateBooking(Long id, Long userId, BookingRequestDto request);

    void deleteBooking(Long id, Long userId);

    BookingResponseDto detailBooking(Long id);

    Page<BookingResponseDto> historyBooking(Long userId, Pageable pageable);

}
