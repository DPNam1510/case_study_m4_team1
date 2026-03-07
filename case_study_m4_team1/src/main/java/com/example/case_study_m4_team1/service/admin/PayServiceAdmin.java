package com.example.case_study_m4_team1.service.admin;

import com.example.case_study_m4_team1.entity.Pay;
import com.example.case_study_m4_team1.repository.admin.IPayRepositoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceAdmin implements IPayServiceAdmin {
    @Autowired
    private IPayRepositoryAdmin payRepository;

    @Override
    public List<Pay> getAll() {
        return payRepository.findAll();
    }
}
