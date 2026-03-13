package com.example.case_study_m4_team1.service.account;

import com.example.case_study_m4_team1.entity.Account;
import com.example.case_study_m4_team1.repository.account.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(Account account){

        account.setPassword(
                passwordEncoder.encode(account.getPassword())
        );

        accountRepository.save(account);
    }
}
