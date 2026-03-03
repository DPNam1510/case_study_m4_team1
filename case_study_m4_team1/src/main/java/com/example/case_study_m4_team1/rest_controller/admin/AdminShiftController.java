package com.example.case_study_m4_team1.rest_controller.admin;

import com.example.case_study_m4_team1.dto.ShiftDto;
import com.example.case_study_m4_team1.entity.Shift;
import com.example.case_study_m4_team1.service.admin.shift.IShiftServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/shifts")
public class AdminShiftController {
    @Autowired
    private IShiftServiceAdmin shiftServiceAdmin;

    @GetMapping("")
    public ResponseEntity<List<ShiftDto>> getAllShift(){
        return ResponseEntity.ok(shiftServiceAdmin.findAll());
    }

}
