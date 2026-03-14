package com.example.case_study_m4_team1.controller.booking;


import com.example.case_study_m4_team1.dto.BookingRequestDto;
import com.example.case_study_m4_team1.dto.BookingResponseDto;
import com.example.case_study_m4_team1.entity.Account;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.exception.BookingException;
import com.example.case_study_m4_team1.security.SecurityUtils;
import com.example.case_study_m4_team1.service.admin.IPayServiceAdmin;
import com.example.case_study_m4_team1.service.admin.IPaymentFieldBookServiceAdmin;
import com.example.case_study_m4_team1.service.booking.IFieldBookService;
import com.example.case_study_m4_team1.service.booking.IFieldsService;
import com.example.case_study_m4_team1.service.booking.IShiftService;
import com.example.case_study_m4_team1.service.user.IUserPaymentFieldBookService;
import com.example.case_study_m4_team1.service.user.IUserStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/booking")
@PreAuthorize("hasRole('USER')")
public class FieldBookController {
    @Autowired
    private IFieldBookService fieldBookService;
    @Autowired
    private IShiftService shiftService;
    @Autowired
    private IFieldsService fieldsService;

    @Autowired
    private IUserStudyService userStudyService;

    @Autowired
    private IPayServiceAdmin payServiceAdmin;

    @Autowired
    private IPaymentFieldBookServiceAdmin paymentFieldBookServiceAdmin;

    @Autowired private IUserPaymentFieldBookService userPaymentFieldBookService;


    @GetMapping("")
    public String bookingHome(Principal principal){
        String username = principal.getName();
        Long userId = userStudyService.getUserIdByUsername(username);
        return "redirect:/booking/history?userId=" + userId;
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam Long userId ,Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("booking", new BookingRequestDto());
        model.addAttribute("fields",fieldsService.getAllFields());
        model.addAttribute("shifts",shiftService.getAllShifts());
        return "booking/create";
    }
    @PostMapping("/create")
    public String createBooking(@RequestParam Long userId,
                                @ModelAttribute BookingRequestDto request,
                                RedirectAttributes redirectAttributes) {
            fieldBookService.createBooking(userId, request);
            redirectAttributes.addFlashAttribute("message", "Booking has been created!");
        return "redirect:/booking/history?userId=" + userId;
    }

    @GetMapping("/detail/{id}")
    public String bookingDetail(@PathVariable Long id, Model model) {
        BookingResponseDto booking = fieldBookService.detailBooking(id);
        model.addAttribute("booking", booking);
        return "booking/detail";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id,
                                 @RequestParam Long userId,
                                 Model model) {
        BookingResponseDto booking = fieldBookService.detailBooking(id);
        BookingRequestDto request = new BookingRequestDto();
        request.setFieldId(booking.getFieldId());
        request.setShiftId(booking.getShiftId());
        request.setDateBook(booking.getDateBook());
        model.addAttribute("booking", request);
        model.addAttribute("bookingId",id);
        model.addAttribute("userId",userId);
        model.addAttribute("fields",fieldsService.getAllFields());
        model.addAttribute("shifts",shiftService.getAllShifts());
        return "booking/update";
    }
    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable Long id,
                                @RequestParam Long userId,
                                @ModelAttribute BookingRequestDto request,
                                RedirectAttributes redirectAttributes) {
            fieldBookService.updateBooking(id, userId, request);
            redirectAttributes.addFlashAttribute("message", "Booking has been updated!");
        return "redirect:/booking/history?userId=" + userId;
    }

    @PostMapping("/delete/{id}")
    public  String deleteBooking(@PathVariable Long id,
                                 @RequestParam Long userId,
                                 RedirectAttributes redirectAttributes) {
        fieldBookService.deleteBooking(id, userId);
        redirectAttributes.addFlashAttribute("message", "Booking has been deleted!");
        return "redirect:/booking/history?userId=" + userId;
    }

    @GetMapping("/history")
    public String historyBooking(@RequestParam Long userId,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<BookingResponseDto> history = fieldBookService.historyBooking(userId, pageable);
        model.addAttribute("history", history);
        model.addAttribute("userId", userId);
        return "booking/history";
    }

    // Hiển thị danh sách booking có thể thanh toán
    @GetMapping("/payment/list")
    public String paymentList(Principal principal, Model model,
                              @RequestParam(defaultValue = "0") int page) {
        Long userId = getCurrentUserId(principal);   // cần implement
        Pageable pageable = PageRequest.of(page, 5);
        Page<BookingResponseDto> bookings = userPaymentFieldBookService.getApprovedBookingsForPayment(userId, pageable);
        model.addAttribute("bookings", bookings);
        return "user/payment_booking_list";
    }

    // Hiển thị form thanh toán cho một booking
    @GetMapping("/payment/{bookingId}")
    public String showPaymentForm(@PathVariable Long bookingId,
                                  Principal principal,
                                  Model model) throws Exception {
        Long userId = getCurrentUserId(principal);
        BookingResponseDto booking = fieldBookService.detailBooking(bookingId);
        if (!booking.getUserId().equals(userId)) {
            throw new Exception("Bạn không có quyền truy cập");
        }
        if (booking.getStatus() != BookingStatus.APPROVED) {
            throw new Exception("Booking chưa được duyệt");
        }
        // Kiểm tra chưa thanh toán
        if (paymentFieldBookServiceAdmin.existsByBookingId(bookingId)) {   // cần thêm method
            throw new Exception("Booking đã thanh toán");
        }
        model.addAttribute("booking", booking);
        model.addAttribute("payTypes", payServiceAdmin.getAll());   // lấy danh sách Pay
        return "user/payment_booking";
    }

    // Xử lý thanh toán
    @PostMapping("/payment/process")
    public String processPayment(@RequestParam Long bookingId,
                                 @RequestParam int payTypeId,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        try {
            userPaymentFieldBookService.createPayment(bookingId, payTypeId, principal.getName());
            redirectAttributes.addFlashAttribute("success", "Thanh toán thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/booking/history?userId=" + getCurrentUserId(principal);
    }

    // Helper lấy userId từ Principal
    private Long getCurrentUserId(Principal principal) {
        Account account = SecurityUtils.getCurrentAccount();  // cần SecurityUtils
        return account.getUsers().getId();
    }
}
