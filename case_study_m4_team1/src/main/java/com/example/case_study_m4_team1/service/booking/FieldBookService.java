package com.example.case_study_m4_team1.service.booking;

import com.example.case_study_m4_team1.dto.BookingRequestDto;
import com.example.case_study_m4_team1.dto.BookingResponseDto;
import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.entity.Shift;
import com.example.case_study_m4_team1.entity.Users;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.enums.RegisterStatus;
import com.example.case_study_m4_team1.repository.booking.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        dto.setFieldName(booking.getField().getName());
        dto.setDateBook(booking.getDateBook());
        dto.setShiftTime(booking.getShift().getStartTime() + " - " +
                booking.getShift().getEndTime());
        dto.setStatus(booking.getStatus());
        return dto;
    }

    @Override
    public BookingResponseDto createBooking(Long userId, BookingRequestDto request) {
        if (request.getDateBook().isBefore(LocalDate.now())) {
            throw new RuntimeException("Not booking past day!");
        }
        List<BookingStatus> statuses = List.of(BookingStatus.PENDING, BookingStatus.APPROVED);

        boolean isFieldBooked = fieldBookRepo.existsByField_IdAndShift_IdAndDateBookAndStatusIn(
                request.getFieldId(),
                request.getShiftId(),
                request.getDateBook(),
                statuses);
        if (isFieldBooked) {
            throw new RuntimeException("The field is booked!");
        }

        boolean isUserBooked = fieldBookRepo.existsByUser_IdAndShift_IdAndDateBookAndStatusIn(
                userId,
                request.getShiftId(),
                request.getDateBook(),
                statuses);
        if (isUserBooked) {
            throw new RuntimeException("The shift is booked!");
        }

        boolean isStudyConflict = classRegisterRepo.existsByUser_IdAndSchedule_Shift_IdAndStatusRegister (
                userId,
                request.getShiftId(),
                RegisterStatus.APPROVED);
        if (isStudyConflict) {
            throw new RuntimeException("Study conflict!");
        }

        Users users = usersRepo.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found!"));
        Fields fields = fieldsRepo.findById(request.getFieldId()).orElseThrow(
                () -> new RuntimeException("Field not found!"));
        Shift shift = shiftRepo.findById(request.getShiftId()).orElseThrow(
                () -> new RuntimeException("Shift not found!"));

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
    public BookingResponseDto updateBooking(Long userId, Long id, BookingRequestDto request) {
        FieldBook fieldBook = fieldBookRepo.findById(id).orElseThrow(
                ()->new RuntimeException("Booking not found!"));
        if (!Objects.equals(fieldBook.getUser().getId(), userId)) {
            throw new RuntimeException("Not edit booking!");
        }
        if (fieldBook.getStatus() == BookingStatus.APPROVED) {
            throw new RuntimeException("Admin approve booking, not edit!");
        }
        List<BookingStatus> statuses = List.of(BookingStatus.PENDING, BookingStatus.APPROVED);
        if(fieldBookRepo.existsByField_IdAndShift_IdAndDateBookAndStatusInAndIdNot(
                request.getFieldId(),
                request.getShiftId(),
                request.getDateBook(),
                statuses,
                id)){
            throw new RuntimeException("The field is booked!");
        }
        fieldBook.setField(fieldsRepo.findById(request.getFieldId()).orElseThrow());
        fieldBook.setShift(shiftRepo.findById(request.getShiftId()).orElseThrow());
        fieldBook.setDateBook(request.getDateBook());
        return convertToDto(fieldBook);
    }

    @Override
    public void deleteBooking(Long id, Long userId) {
        FieldBook fieldBook = fieldBookRepo.findCancelableBooking(id,userId,
                BookingStatus.APPROVED).orElseThrow(
                        () -> new RuntimeException("Booking not cancelled!"));
        LocalDateTime bookingTime = LocalDateTime.of(fieldBook.getDateBook(),
                                                     fieldBook.getShift().getStartTime());
        if (LocalDateTime.now().isAfter(bookingTime.minusHours(1))) {
            throw new RuntimeException("Over time cancel!");
        }
        fieldBook.setStatus(BookingStatus.CANCELED);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDto detailBooking(Long id){
        FieldBook fieldBook = fieldBookRepo.findDetailById(id).orElseThrow(
                () -> new RuntimeException("Booking not found!"));
        return convertToDto(fieldBook);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingResponseDto> historyBooking(Long userId, Pageable pageable) {
        return fieldBookRepo.findAllByUser_IdOrderByDateBookDesc(
                userId,pageable).map(this::convertToDto);
    }
}
