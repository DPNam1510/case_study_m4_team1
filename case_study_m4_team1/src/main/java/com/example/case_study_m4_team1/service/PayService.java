package com.example.case_study_m4_team1.service;

import com.example.case_study_m4_team1.entity.Pay;
import com.example.case_study_m4_team1.repository.IPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayService implements IPayService{
    @Autowired
    private IPayRepository payRepository;

    @Override
    public List<Pay> getAll() {
        return payRepository.findAll();
    }
}
