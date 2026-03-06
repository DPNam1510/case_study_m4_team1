package com.example.case_study_m4_team1.service.admin.field_book;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.enums.FieldStatus;
import com.example.case_study_m4_team1.exception.BookingException;
import com.example.case_study_m4_team1.exception.NotFoundException;
import com.example.case_study_m4_team1.repository.admin.field_book.IFieldBookRepositoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
public class FieldBookServiceAdmin implements IFieldBookServiceAdmin {
    @Autowired
    private IFieldBookRepositoryAdmin fieldBookRepositoryAdmin;


    @Override
    public Page<FieldBook> search(String user, String field, LocalDate date, Pageable pageable) {
        return fieldBookRepositoryAdmin.search("%"+user+"%", "%"+field+"%", date, pageable);
    }


    @Override
    public Page<FieldBook> searchPending(String user, String field, LocalDate date, BookingStatus status, Pageable pageable) {
        return fieldBookRepositoryAdmin.searchPending("%"+user+"%", "%"+field+"%", date,status, pageable);
    }


    @Override
    public FieldBook findById(long id) {
        return fieldBookRepositoryAdmin.findById(id).orElse(null);
    }

    @Override
    public void approveBooking(long id) {
        FieldBook booking = fieldBookRepositoryAdmin.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking field id: " + id + " not found"));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BookingException("Only PENDING booking can be approved");
        }

        if (booking.getField().getStatus() == FieldStatus.NOT_AVAILABLE) {
            throw new BookingException("Field is under maintenance");
        }

        boolean isConflict = fieldBookRepositoryAdmin
                .existsByFieldIdAndDateBookAndShiftIdAndStatusAndIdNot(
                        booking.getField().getId(),
                        booking.getDateBook(),
                        booking.getShift().getId(),
                        BookingStatus.APPROVED,
                        booking.getId()
                );

        if (isConflict) {
            throw new BookingException("Time slot already booked");
        }

        booking.setStatus(BookingStatus.APPROVED);
        fieldBookRepositoryAdmin.save(booking);
    }

    @Override
    public void cancelBooking(long id) {
        FieldBook booking = fieldBookRepositoryAdmin.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking field id: " + id + " not found"));

        BookingStatus status = booking.getStatus();

        if (status == BookingStatus.CANCELED) {
            throw new BookingException("Booking already canceled");
        }
        booking.setStatus(BookingStatus.CANCELED);
        fieldBookRepositoryAdmin.save(booking);
    }
}
