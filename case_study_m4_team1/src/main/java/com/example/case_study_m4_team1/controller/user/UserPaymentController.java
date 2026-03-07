package com.example.case_study_m4_team1.controller.user;

import com.example.case_study_m4_team1.dto.PaymentFieldBookDto;
import com.example.case_study_m4_team1.enums.PaymentStatus;
import com.example.case_study_m4_team1.service.IPayService;
import com.example.case_study_m4_team1.service.IPaymentFieldBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("user/payments")
public class UserPaymentController {
    @Autowired
    private IPaymentFieldBookService paymentFieldBookService;
    @Autowired
    private IPayService payService;

//    @GetMapping
//    public String showPaymentHistory(Model model, Principal principal){
//        Long userId = userService.findByUsername(principal.getName()).getId();
//        List<PaymentFieldBookDto> paymentFieldBookDtoS = paymentFieldBookService.findByUserId(userId);
//        model.addAttribute("paymentFieldBookDtoS", paymentFieldBookDtoS);
//        return "user/payment/history";
//    }
//    @GetMapping
//    public String showCreate(Model model){
//        model.addAttribute("payList",payService.getAll());
//        return "user/payment/create";
//    }
//
//    @PostMapping("/create")
//    public String create(@RequestParam long fieldBookId,
//                         @RequestParam int payId){
//        paymentFieldBookService.createPayment(fieldBookId, payId);
//        return "redirect:/user/booking";
//    }

}
