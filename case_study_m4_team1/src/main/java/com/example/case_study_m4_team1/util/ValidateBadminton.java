package com.example.case_study_m4_team1.util;

import com.example.case_study_m4_team1.entity.Account;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ValidateBadminton implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        // --- username ---
        if(account.getUsername() == null || account.getUsername().equals("")) {
            errors.rejectValue("username", null, "Username cannot be empty");
        } else if(account.getUsername().length() < 5) {
            errors.rejectValue("username", null, "Username must be at least 5 characters");
        } else if (!account.getUsername().matches("^\\w+$")) {
            errors.rejectValue("username", null, "Username cannot contain special characters other than '_'");
        }

        // --- password ---
        if(account.getPassword() == null || account.getPassword().equals("")) {
            errors.rejectValue("password", null, "Password cannot be empty");
        } else if(account.getPassword().length() < 3) {
            errors.rejectValue("password", null, "Password must be at least 6 characters");
        }

        // --- email ---
        if(account.getEmail() == null || account.getEmail().equals("")) {
            errors.rejectValue("email", null, "Email cannot be empty");
        } else if(!account.getEmail().matches("^\\w+@([a-z]+.com)(.vn)?$")) {
            errors.rejectValue("email", null, "Email format is invalid");
        }

        // --- phone ---
        if(account.getPhone() == null || account.getPhone().equals("")) {
            errors.rejectValue("phone", null, "Phone cannot be empty");
        } else if(!account.getPhone().matches("^[0-9]{10,11}$")) {
            errors.rejectValue("phone", null, "Phone must be at least 10, max 11 numbers");
        }

    }
}
