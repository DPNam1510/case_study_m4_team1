package com.example.case_study_m4_team1.service.admin.field;

import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.enums.FieldStatus;

import java.util.List;

public interface IFieldsServiceAdmin {
    List<Fields> findAll();
    void setFieldsMaintenance (int id, FieldStatus status);
}
