package com.example.case_study_m4_team1.service.admin.field;

import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.enums.FieldStatus;
import com.example.case_study_m4_team1.exception.FieldException;
import com.example.case_study_m4_team1.exception.NotFoundException;
import com.example.case_study_m4_team1.repository.admin.field.IFieldsRepositoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FieldsServiceAdmin implements IFieldsServiceAdmin {
    @Autowired
    private IFieldsRepositoryAdmin fieldsRepositoryAdmin;

    @Override
    public List<Fields> findAll() {
        return fieldsRepositoryAdmin.findAll();
    }

    @Override
    public void setFieldsMaintenance(int id, FieldStatus status) {
        Fields field = fieldsRepositoryAdmin.findById(id)
                .orElseThrow(() -> new NotFoundException("Field with id: "+id+" not found"));

        if (field.getStatus() == status){
            throw new FieldException("Field is already "+status);
        }
        field.setStatus(status);
        fieldsRepositoryAdmin.save(field);
    }
}
