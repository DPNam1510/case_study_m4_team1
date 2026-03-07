package com.example.case_study_m4_team1.controller.admin;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.enums.BookingStatus;
import com.example.case_study_m4_team1.service.admin.field_book.IFieldBookServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("admin/field_books")
public class AdminFieldBookController {
    @Autowired
    private IFieldBookServiceAdmin fieldBookServiceAdmin;

    @GetMapping
    public String showApproved(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "searchUser", defaultValue = "") String user,
                       @RequestParam(value = "searchField", defaultValue = "") String field,
                       @RequestParam(value = "searchDate", required = false)
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           LocalDate date){
        Sort sort = Sort.by("date_book").ascending();
        Pageable pageable = PageRequest.of(page,5,sort);
        Page<FieldBook> fieldBookPage = fieldBookServiceAdmin.searchApprove(user,field,date,BookingStatus.APPROVED,pageable);
        model.addAttribute("fieldBookPage",fieldBookPage);
        model.addAttribute("searchUser",user);
        model.addAttribute("searchField",field);
        model.addAttribute("searchDate",date);
        return "admin/field_book/list";
    }

    @GetMapping("/pending")
    public String showPending(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "searchUser", defaultValue = "") String user,
                              @RequestParam(value = "searchField", defaultValue = "") String field,
                              @RequestParam(value = "searchDate", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                  LocalDate date){
        Pageable pageable = PageRequest.of(page, 5, Sort.by("date_book").ascending());
        Page<FieldBook> fieldBookPage = fieldBookServiceAdmin.searchPending(user,field,date,BookingStatus.PENDING,pageable);
        model.addAttribute("fieldBookPage", fieldBookPage);
        model.addAttribute("searchUser",user);
        model.addAttribute("searchField",field);
        model.addAttribute("searchDate",date);
        return "admin/field_book/pending_list";
    }

    @GetMapping("/canceled")
    public String showCanceled(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "searchUser", defaultValue = "") String user,
                              @RequestParam(value = "searchField", defaultValue = "") String field,
                              @RequestParam(value = "searchDate", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                  LocalDate date){
        Pageable pageable = PageRequest.of(page, 5, Sort.by("date_book").ascending());
        Page<FieldBook> fieldBookPage = fieldBookServiceAdmin.searchCanceled(user,field,date,BookingStatus.CANCELED,pageable);
        model.addAttribute("fieldBookPage", fieldBookPage);
        model.addAttribute("searchUser",user);
        model.addAttribute("searchField",field);
        model.addAttribute("searchDate",date);
        return "admin/field_book/canceled_list";
    }

    @PostMapping("{id}/approve")
    public String approve(@PathVariable long id){
        fieldBookServiceAdmin.approveBooking(id);
        return "redirect:/admin/field_books/pending";
    }

    @PostMapping("{id}/cancel")
    public String cancel(@PathVariable long id){
        fieldBookServiceAdmin.cancelBooking(id);
        return "redirect:/admin/field_books";
    }
}
