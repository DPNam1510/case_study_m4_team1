package com.example.case_study_m4_team1.service.booking;

import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.repository.booking.IFieldsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldsService implements IFieldsService {
    @Autowired
    private IFieldsRepo fieldsRepo;

    @Override
    public List<Fields> getAllFields() {
        return fieldsRepo.findAllByOrderByNameAsc();
    }
}
