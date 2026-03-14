package com.example.case_study_m4_team1.controller.admin;

import com.example.case_study_m4_team1.dto.PaymentFieldBookDto;
import com.example.case_study_m4_team1.dto.PaymentRegisterDto;
import com.example.case_study_m4_team1.enums.PaymentStatus;
import com.example.case_study_m4_team1.service.admin.IPaymentFieldBookServiceAdmin;
import com.example.case_study_m4_team1.service.admin.IPaymentRegisterServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/payments")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPaymentFieldBookController {
    @Autowired
    private IPaymentFieldBookServiceAdmin paymentFieldBookService;
    @Autowired
    IPaymentRegisterServiceAdmin paymentRegisterService;

    @GetMapping
    public String show(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "searchUser", defaultValue = "") String user,
                       @RequestParam(value = "searchField", defaultValue = "") String field,
                       @RequestParam(value = "status", defaultValue = "")PaymentStatus status){
        Pageable pageable = PageRequest.of(page,5, Sort.by("date").ascending());
        Page<PaymentFieldBookDto> paymentFieldBookDtoS = paymentFieldBookService.searchPayment(user,field,status,pageable);
        model.addAttribute("paymentFieldBookDtoS",paymentFieldBookDtoS);
        model.addAttribute("searchUser",user);
        model.addAttribute("searchField",field);
        model.addAttribute("status",status);
        return "admin/payment/payment_field_book/list";
    }

    @GetMapping("/payment_register")
    public String showPaymentRegister(Model model,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "searchClassName", defaultValue = "") String className,
                                      @RequestParam(value = "searchUser", defaultValue = "") String user,
                                      @RequestParam(value = "searchTeacher", defaultValue = "") String teacher,
                                      @RequestParam(value = "status", defaultValue = "")PaymentStatus status){
        Pageable pageable = PageRequest.of(page,5, Sort.by("date").ascending());
        Page<PaymentRegisterDto> paymentRegisterDtoS = paymentRegisterService.searchPayment(className,user,teacher,status,pageable);
        model.addAttribute("paymentRegisterDtoS",paymentRegisterDtoS);
        model.addAttribute("searchClassName",className);
        model.addAttribute("searchUser",user);
        model.addAttribute("searchTeacher",teacher);
        model.addAttribute("status",status);
        return "admin/payment/payment_register/list";
    }

    @PostMapping("/set_paid")
    public String setPaidPayment(@RequestParam long id,
                                 RedirectAttributes redirectAttributes){
        paymentFieldBookService.adminSetPaid(id);
        redirectAttributes.addFlashAttribute("mess","Update status success !!!");
        return "redirect:/admin/payments";
    }

    @PostMapping("/set_paid_register")
    public String setPaidPaymentRegister(@RequestParam long id,
                                 RedirectAttributes redirectAttributes){
        paymentRegisterService.adminSetPaid(id);
        redirectAttributes.addFlashAttribute("mess","Update status success !!!");
        return "redirect:/admin/payments/payment_register";
    }

}
