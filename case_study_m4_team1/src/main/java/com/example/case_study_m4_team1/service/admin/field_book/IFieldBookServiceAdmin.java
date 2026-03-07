package com.example.case_study_m4_team1.service.admin.field_book;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface IFieldBookServiceAdmin {
    Page<FieldBook> searchApprove(@Param("searchUser") String user,
                              @Param("searchField") String field,
                              @Param("searchDate") LocalDate date,
                           BookingStatus status,
                              Pageable pageable);

    Page<FieldBook> searchPending(
            @Param("searchUser") String user,
            @Param("searchField") String field,
            @Param("searchDate") LocalDate date,
            BookingStatus status,
            Pageable pageable);

    Page<FieldBook> searchCanceled(
            @Param("searchUser") String user,
            @Param("searchField") String field,
            @Param("searchDate") LocalDate date,
            BookingStatus status,
            Pageable pageable);

    FieldBook findById(long id);
    void approveBooking(long id);
    void cancelBooking(long id);
}
