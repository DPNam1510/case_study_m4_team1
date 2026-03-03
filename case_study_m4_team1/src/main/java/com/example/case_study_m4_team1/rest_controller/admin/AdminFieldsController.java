package com.example.case_study_m4_team1.rest_controller.admin;

import com.example.case_study_m4_team1.entity.Fields;
import com.example.case_study_m4_team1.enums.FieldStatus;
import com.example.case_study_m4_team1.service.admin.field.IFieldsServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/fields")
public class AdminFieldsController {
    @Autowired
    private IFieldsServiceAdmin fieldsServiceAdmin;

    @GetMapping("")
    public ResponseEntity<List<Fields>> getAllFields() {
        List<Fields> fieldsList = fieldsServiceAdmin.findAll();
        if (fieldsList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fieldsList, HttpStatus.OK);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<String> updateFieldStatus(
            @PathVariable int id,
            @RequestParam FieldStatus status
    ) {
        fieldsServiceAdmin.setFieldsMaintenance(id, status);
        return ResponseEntity.ok("Field status updated to " + status);
    }
}
