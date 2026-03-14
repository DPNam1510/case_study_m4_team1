package com.example.case_study_m4_team1.service.user;

import com.example.case_study_m4_team1.dto.BookingResponseDto;
import com.example.case_study_m4_team1.entity.*;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.enums.PaymentStatus;
import com.example.case_study_m4_team1.repository.account.IAccountRepository;
import com.example.case_study_m4_team1.repository.admin.IPayRepositoryAdmin;
import com.example.case_study_m4_team1.repository.admin.IPaymentFieldBookRepositoryAdmin;
import com.example.case_study_m4_team1.repository.booking.IFieldBookRepo;
import com.example.case_study_m4_team1.repository.user.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserPaymentFieldBookService implements IUserPaymentFieldBookService {
    @Autowired
    private IFieldBookRepo fieldBookRepo;
    @Autowired private IPaymentFieldBookRepositoryAdmin paymentRepo;
    @Autowired private IPayRepositoryAdmin payRepo;
    @Autowired private IAccountRepository accountRepository;
    @Autowired private IUsersRepository usersRepository;

    private BookingResponseDto convertToDto(FieldBook fb) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(fb.getId());
        dto.setUserId(fb.getUser().getId());
        dto.setFieldId(fb.getField().getId());
        dto.setShiftId(fb.getShift().getId());
        dto.setFieldName(fb.getField().getName());
        dto.setDateBook(fb.getDateBook());
        dto.setShiftTime(fb.getShift().getStartTime() + " - " + fb.getShift().getEndTime());
        dto.setStatus(fb.getStatus());
        return dto;
    }

    @Override
    public Page<BookingResponseDto> getApprovedBookingsForPayment(Long userId, Pageable pageable) {
        Page<FieldBook> bookings = fieldBookRepo.findApprovedAndNotPaidByUserId(userId, BookingStatus.APPROVED, pageable);
        return bookings.map(this::convertToDto);
    }

    @Override
    public void createPayment(Long bookingId, int payTypeId, String username) throws Exception {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Tài khoản không tồn tại"));
        Users user = usersRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new Exception("Người dùng không tồn tại"));

        FieldBook booking = fieldBookRepo.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking không tồn tại"));

        if (booking.getUser().getId() != user.getId()) {
            throw new Exception("Bạn không có quyền thanh toán booking này");
        }
        if (booking.getStatus() != BookingStatus.APPROVED) {
            throw new Exception("Chỉ được thanh toán cho booking đã được duyệt");
        }
        if (paymentRepo.existsPayment(bookingId) > 0) {
            throw new Exception("Booking này đã được thanh toán");
        }

        Pay pay = payRepo.findById(payTypeId)
                .orElseThrow(() -> new Exception("Hình thức thanh toán không hợp lệ"));

        PaymentFieldBook payment = new PaymentFieldBook();
        payment.setFieldBook(booking);
        payment.setPay(pay);
        payment.setStatus(PaymentStatus.PAID);   // giả lập thanh toán thành công
        paymentRepo.save(payment);
    }
}
