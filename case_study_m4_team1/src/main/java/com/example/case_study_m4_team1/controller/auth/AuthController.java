package com.example.case_study_m4_team1.controller.auth;

import com.example.case_study_m4_team1.entity.Account;
import com.example.case_study_m4_team1.repository.role.IRoleRepository;
import com.example.case_study_m4_team1.repository.account.IAccountRepository;
import com.example.case_study_m4_team1.util.ValidateBadminton;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final IAccountRepository accountRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(IAccountRepository accountRepository,
                          IRoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Hiển thị trang login, map param error/logout sang model
    @GetMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout) {
        if (error != null) model.addAttribute("error", "Username or password is not exists");
        if (logout != null) model.addAttribute("success", "Logout successful");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("account", new Account());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(Account account,
                           BindingResult bindingResult,
                           RedirectAttributes redirect) {

        new ValidateBadminton().validate(account,bindingResult);
        if (bindingResult.hasFieldErrors()){
            return "auth/register";
        }

        if(accountRepository.existsByUsername(account.getUsername())){
            redirect.addFlashAttribute("error", "Username already exists");
            return "redirect:/register";
        }

        account.setRole(roleRepository.findByName("USER"));

        account.setPassword(passwordEncoder.encode(account.getPassword()));

        accountRepository.save(account);

        redirect.addFlashAttribute("success", "Register successful. Please login");

        return "redirect:/login";
    }
}