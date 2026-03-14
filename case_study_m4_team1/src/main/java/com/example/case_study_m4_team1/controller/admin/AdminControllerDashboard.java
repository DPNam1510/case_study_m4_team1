package com.example.case_study_m4_team1.controller.admin;

import com.example.case_study_m4_team1.entity.Account;
import com.example.case_study_m4_team1.entity.Teacher;
import com.example.case_study_m4_team1.repository.account.IAccountRepository;
import com.example.case_study_m4_team1.service.teacher.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminControllerDashboard {


    @Autowired
    private IAccountRepository accountRepository;

    private Optional<Account> getCurrentAdmin(Principal principal){
        String username = principal.getName();
        return accountRepository.findByUsername(username);
    }

    @GetMapping
    public String dashboard(Principal principal, Model model) {

        Optional<Account> account = getCurrentAdmin(principal);

        model.addAttribute("admin", account.get().getUsername());

        return "admin/dashboard_admin";
    }
}
