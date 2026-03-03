package com.example.case_study_m4_team1.service.admin.field_book;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.enums.FieldStatus;
import com.example.case_study_m4_team1.exception.BookingException;
import com.example.case_study_m4_team1.exception.NotFoundException;
import com.example.case_study_m4_team1.repository.admin.field_book.IFieldBookRepositoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FieldBookServiceAdmin implements IFieldBookServiceAdmin {
    @Autowired
    private IFieldBookRepositoryAdmin fieldBookRepositoryAdmin;

    @Override
    public List<FieldBook> findAll() {
        return fieldBookRepositoryAdmin.findAll();
    }

    @Override
    public void approveBooking(long id) {
        FieldBook booking = fieldBookRepositoryAdmin.findById(id).orElseThrow(
                () -> new NotFoundException("Booking field id: "+id+" not found"));
        BookingStatus status = booking.getStatus();

        if (booking.getField().getStatus() == FieldStatus.NOT_AVAILABLE){
            throw new BookingException("Field is under maintenance");
        }
        if (status == BookingStatus.APPROVED){
            throw new BookingException("Booking is already approved");
        }if (status == BookingStatus.CANCELED){
            throw new BookingException("Booking is canceled");
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
}
