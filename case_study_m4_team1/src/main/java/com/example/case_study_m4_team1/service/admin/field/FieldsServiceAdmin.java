package com.example.case_study_m4_team1.service.admin.field;

import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.enums.FieldStatus;
import com.example.case_study_m4_team1.exception.FieldException;
import com.example.case_study_m4_team1.exception.NotFoundException;
import com.example.case_study_m4_team1.repository.admin.field.IFieldsRepositoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FieldsServiceAdmin implements IFieldsServiceAdmin {
    @Autowired
    private IFieldsRepositoryAdmin fieldsRepositoryAdmin;


    @Override
    public Page<Fields> findByNameContaining(String name, Pageable pageable) {
        return fieldsRepositoryAdmin.findByNameContaining(name,pageable);
    }

    @Override
    public Page<Fields> search(String name, double price, Pageable pageable) {
        return fieldsRepositoryAdmin.search("%"+name+"%",price,pageable);
    }

    @Override
    public Fields findById(int id) {
        return fieldsRepositoryAdmin.findById(id).orElse(null);
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
