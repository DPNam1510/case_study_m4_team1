package com.example.case_study_m4_team1.service.booking;

import com.example.case_study_m4_team1.dto.BookingRequestDto;
import com.example.case_study_m4_team1.dto.BookingResponseDto;
import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.entity.Shift;
import com.example.case_study_m4_team1.entity.Users;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import com.example.case_study_m4_team1.exception.BookingException;
import com.example.case_study_m4_team1.repository.booking.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class FieldBookService implements IFieldBookService {
    @Autowired
    private IFieldBookRepo fieldBookRepo;
    @Autowired
    private IFieldsRepo fieldsRepo;
    @Autowired
    private IShiftRepo shiftRepo;
    @Autowired
    private IUsersRepo usersRepo;
    @Autowired
    private IClassRegisterRepo classRegisterRepo;

    private BookingResponseDto convertToDto(FieldBook booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setFieldId(booking.getField().getId());
        dto.setShiftId(booking.getShift().getId());
        dto.setFieldName(booking.getField().getName());
        dto.setDateBook(booking.getDateBook());
        dto.setShiftTime(booking.getShift().getStartTime() + " - " +
                booking.getShift().getEndTime());
        dto.setStatus(booking.getStatus());
        return dto;
    }

    @Override
    public BookingResponseDto createBooking(Long userId, BookingRequestDto request) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        if (request.getDateBook().isBefore(today)) {
            throw new BookingException("Not booking past day!");
        }
        if (request.getDateBook().isEqual(today)) {
            Shift shift = shiftRepo.findById(request.getShiftId()).orElseThrow();
            if (shift.getStartTime().isBefore(now)) {
                throw new BookingException("Not booking past shift!");
            }
        }
        List<BookingStatus> statuses = List.of(BookingStatus.PENDING, BookingStatus.APPROVED);

        boolean isFieldBooked = fieldBookRepo.existsByField_IdAndShift_IdAndDateBookAndStatusIn(
                request.getFieldId(),
                request.getShiftId(),
                request.getDateBook(),
                statuses);
        if (isFieldBooked) {
            throw new BookingException("The field is booked!");
        }

        boolean isUserBooked = fieldBookRepo.existsByUser_IdAndShift_IdAndDateBookAndStatusIn(
                userId,
                request.getShiftId(),
                request.getDateBook(),
                statuses);
        if (isUserBooked) {
            throw new BookingException("The shift is booked!");
        }

        boolean isStudyConflict = classRegisterRepo.existsByUser_IdAndStudySchedule_Shift_IdAndStatusRegister (
                userId,
                request.getShiftId(),
                RegisterStatus.APPROVED);
        if (isStudyConflict) {
            throw new BookingException("Study conflict!");
        }

        Users users = usersRepo.findById(userId).orElseThrow(
                () -> new BookingException("User not found!"));
        Fields fields = fieldsRepo.findById(request.getFieldId()).orElseThrow(
                () -> new BookingException("Field not found!"));
        Shift shift = shiftRepo.findById(request.getShiftId()).orElseThrow(
                () -> new BookingException("Shift not found!"));

        FieldBook fieldBook = new FieldBook();
        fieldBook.setUser(users);
        fieldBook.setField(fields);
        fieldBook.setShift(shift);
        fieldBook.setDateBook(request.getDateBook());
        fieldBook.setStatus(BookingStatus.PENDING);

        fieldBookRepo.save(fieldBook);

        return convertToDto(fieldBook);
    }

    @Override
    public BookingResponseDto updateBooking(Long id,Long userId, BookingRequestDto request) {
        if (request.getDateBook().isBefore(LocalDate.now())) {
            throw new BookingException("Not booking past day!");
        }
        FieldBook fieldBook = fieldBookRepo.findById(id).orElseThrow(
                ()->new BookingException("Booking not found!"));
        if (!Objects.equals(fieldBook.getUser().getId(), userId)) {
            throw new BookingException("Not edit booking!");
        }
        if (fieldBook.getStatus() == BookingStatus.APPROVED) {
            throw new BookingException("Admin approve booking, not edit!");
        }
        List<BookingStatus> statuses = List.of(BookingStatus.PENDING, BookingStatus.APPROVED);
        if(fieldBookRepo.existsByField_IdAndShift_IdAndDateBookAndStatusInAndIdNot(
                request.getFieldId(),
                request.getShiftId(),
                request.getDateBook(),
                statuses,
                id)){
            throw new BookingException("The field is booked!");
        }
        fieldBook.setField(fieldsRepo.findById(request.getFieldId()).orElseThrow());
        fieldBook.setShift(shiftRepo.findById(request.getShiftId()).orElseThrow());
        fieldBook.setDateBook(request.getDateBook());
        fieldBookRepo.save(fieldBook);
        return convertToDto(fieldBook);
    }

    @Override
    public void deleteBooking(Long id, Long userId) {
        FieldBook fieldBook = fieldBookRepo.findCancelableBooking(id,userId,
                List.of(BookingStatus.PENDING, BookingStatus.APPROVED)).orElseThrow(
                        () -> new BookingException("Booking not cancelled!"));
        LocalDateTime bookingTime = LocalDateTime.of(fieldBook.getDateBook(),
                                                     fieldBook.getShift().getStartTime());
        if (LocalDateTime.now().isAfter(bookingTime.minusHours(1))) {
            throw new BookingException("Over time cancel!");
        }
        fieldBook.setStatus(BookingStatus.CANCELED);
        fieldBookRepo.save(fieldBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDto detailBooking(Long id){
        FieldBook fieldBook = fieldBookRepo.findDetailById(id).orElseThrow(
                () -> new BookingException("Booking not found!"));
        return convertToDto(fieldBook);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingResponseDto> historyBooking(Long userId, Pageable pageable) {
        return fieldBookRepo.findAllByUser_IdOrderByDateBookDesc(
                userId,pageable).map(this::convertToDto);
    }
}
