package com.example.case_study_m4_team1.controller.booking;

import com.example.case_study_m4_team1.exception.BookingException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(BookingException.class)
    public String handleBookingException(BookingException e, Model model){
        model.addAttribute("errorBooking", e.getMessage());
        return "booking/errorBooking";
    }
//    public String handleBookingException(BookingException e,
//                                         RedirectAttributes redirectAttributes){
//        redirectAttributes.addFlashAttribute("errorBooking", e.getMessage());
//        return "redirect:/booking/history?userId=";
//    }

}
