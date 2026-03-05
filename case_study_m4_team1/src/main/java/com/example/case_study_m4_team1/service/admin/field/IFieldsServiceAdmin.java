package com.example.case_study_m4_team1.service.admin.field;

import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.enums.FieldStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


public interface IFieldsServiceAdmin {
    Page<Fields> findByNameContaining(String name, Pageable pageable);
    Page<Fields> search(@Param("searchName") String name,
                        @Param("searchPrice") double price,
                        Pageable pageable);
    Fields findById(int id);
    void setFieldsMaintenance (int id, FieldStatus status);
}
