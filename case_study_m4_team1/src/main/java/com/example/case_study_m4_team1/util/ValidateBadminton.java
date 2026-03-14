package com.example.case_study_m4_team1.util;

import com.example.case_study_m4_team1.dto.RegisterAccountDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ValidateBadminton implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterAccountDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RegisterAccountDto dto = (RegisterAccountDto) target;

        if(dto.getUsername() == null || dto.getUsername().equals("")) {
            errors.rejectValue("username", null, "Username cannot be empty");
        } else if(dto.getUsername().length() < 5) {
            errors.rejectValue("username", null, "Username must be at least 5 characters");
        } else if (!dto.getUsername().matches("^\\w+$")) {
            errors.rejectValue("username", null, "Username cannot contain special characters other than '_'");
        }

        // password
        if(dto.getPassword() == null || dto.getPassword().equals("")) {
            errors.rejectValue("password", null, "Password cannot be empty");
        } else if(dto.getPassword().length() < 6) {
            errors.rejectValue("password", null, "Password must be at least 6 characters");
        }

        // email
        if(dto.getEmail() == null || dto.getEmail().equals("")) {
            errors.rejectValue("email", null, "Email cannot be empty");
        } else if(!dto.getEmail().matches("^\\w+@([a-z]+\\.com)(\\.vn)?$")) {
            errors.rejectValue("email", null, "Email format is invalid");
        }

        // phone
        if(dto.getPhone() == null || dto.getPhone().equals("")) {
            errors.rejectValue("phone", null, "Phone cannot be empty");
        } else if(!dto.getPhone().matches("^[0-9]{10,11}$")) {
            errors.rejectValue("phone", null, "Phone must be 10–11 digits");
        }

        // full name
        if(dto.getFullName() == null || dto.getFullName().trim().equals("")) {
            errors.rejectValue("fullName", null, "Full name cannot be empty");
        } else if(dto.getFullName().length() < 3) {
            errors.rejectValue("fullName", null, "Full name must be at least 3 characters");
        } else if(!dto.getFullName().matches("^\\p{L}+(\\s\\p{L}+)*$")) {
            errors.rejectValue("fullName", null, "Full name cannot contain numbers or special characters");
        }
    }
}