package com.example.case_study_m4_team1.controller.booking;


import com.example.case_study_m4_team1.dto.BookingRequestDto;
import com.example.case_study_m4_team1.dto.BookingResponseDto;
import com.example.case_study_m4_team1.service.booking.IFieldBookService;
import com.example.case_study_m4_team1.service.booking.IFieldsService;
import com.example.case_study_m4_team1.service.booking.IShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/booking")
public class FieldBookController {
    @Autowired
    private IFieldBookService fieldBookService;
    @Autowired
    private IShiftService shiftService;
    @Autowired
    private IFieldsService fieldsService;

    @GetMapping("")
    public String bookingHome(@RequestParam(defaultValue = "1") Long userId){
        return "redirect:/booking/history?userId="+userId;
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
        try {
            fieldBookService.createBooking(userId, request);
            redirectAttributes.addFlashAttribute("Success", "Booking has been created!");
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("Error",e.getMessage());
        }
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
                                @ModelAttribute BookingRequestDto request) {
        fieldBookService.updateBooking(id, userId, request);
        return "redirect:/booking/history?userId=" + userId;
    }

    @GetMapping("/delete/{id}")
    public  String deleteBooking(@PathVariable Long id,
                                 @RequestParam Long userId) {
        fieldBookService.deleteBooking(id, userId);
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

}
