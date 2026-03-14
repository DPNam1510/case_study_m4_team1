package com.example.case_study_m4_team1.controller.auth;

import com.example.case_study_m4_team1.dto.RegisterAccountDto;
import com.example.case_study_m4_team1.entity.Account;
import com.example.case_study_m4_team1.entity.Users;
import com.example.case_study_m4_team1.repository.role.IRoleRepository;
import com.example.case_study_m4_team1.repository.account.IAccountRepository;
import com.example.case_study_m4_team1.repository.user.IUsersRepository;
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
//    //fix user khi login
    private final IUsersRepository usersRepository;

    public AuthController(IAccountRepository accountRepository,
                          IRoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          IUsersRepository usersRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
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
    public String showRegister(Model model) {
        model.addAttribute("registerAccountDto", new RegisterAccountDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterAccountDto dto,
                           BindingResult bindingResult,
                           RedirectAttributes redirect) {
        new ValidateBadminton().validate(dto,bindingResult);
        if (bindingResult.hasFieldErrors()){
            return "auth/register";
        }

        if(accountRepository.existsByUsername(dto.getUsername())){
            redirect.addFlashAttribute("error", "Username already exists");
            return "redirect:/register";
        }

        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setEmail(dto.getEmail());
        account.setPhone(dto.getPhone());
        account.setRole(roleRepository.findByName("USER"));

        Account savedAccount = accountRepository.save(account);

        Users user = new Users();
        user.setAccount(savedAccount);
        user.setName(dto.getFullName());

        usersRepository.save(user);

        redirect.addFlashAttribute("success", "Register successful");

        return "redirect:/login";
    }
}