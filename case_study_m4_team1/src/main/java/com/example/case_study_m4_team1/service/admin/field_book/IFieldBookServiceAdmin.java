package com.example.case_study_m4_team1.service.admin.field_book;

import com.example.case_study_m4_team1.entity.FieldBook;

import java.util.List;

public interface IFieldBookServiceAdmin {
    List<FieldBook> findAll();
    void approveBooking(long id);
}
