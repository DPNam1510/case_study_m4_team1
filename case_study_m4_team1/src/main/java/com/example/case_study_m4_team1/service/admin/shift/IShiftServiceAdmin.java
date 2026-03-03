package com.example.case_study_m4_team1.service.admin.shift;

import com.example.case_study_m4_team1.dto.ShiftDto;
import com.example.case_study_m4_team1.entity.Shift;

import java.util.List;

public interface IShiftServiceAdmin {
    List<ShiftDto> findAll();
}
