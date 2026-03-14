package com.example.case_study_m4_team1.controller.admin;

import com.example.case_study_m4_team1.exception.BookingException;
import com.example.case_study_m4_team1.exception.FieldException;
import com.example.case_study_m4_team1.exception.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalController {
//    @ExceptionHandler(BookingException.class)
//    public String handleOutOfStock(Model model) {
//        model.addAttribute("error", "Fails booking");
//        return "error";
//    }

    @ExceptionHandler(FieldException.class)
    public String handleInvalidTransfer(Model model) {
        model.addAttribute("error", "Field was booked");
        return "error";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(Model model) {
        model.addAttribute("error", "Not found data or maintenance");
        return "error";
    }
}
