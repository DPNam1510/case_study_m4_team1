package com.example.case_study_m4_team1.service.admin.shift;

import com.example.case_study_m4_team1.dto.ShiftDto;
import com.example.case_study_m4_team1.entity.Shift;
import com.example.case_study_m4_team1.repository.admin.shift.IShiftRepositoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShiftServiceAdmin implements IShiftServiceAdmin {
    @Autowired
    private IShiftRepositoryAdmin shiftRepositoryAdmin;

    @Override
    public List<ShiftDto> findAll() {
        return shiftRepositoryAdmin.findAll()
                .stream()
                .map(shift -> new ShiftDto(
                        shift.getId(),
                        shift.getStartTime(),
                        shift.getEndTime()
                ))
                .toList();
    }
}
