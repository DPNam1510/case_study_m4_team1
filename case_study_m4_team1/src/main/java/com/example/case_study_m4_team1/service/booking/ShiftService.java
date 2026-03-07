package com.example.case_study_m4_team1.service.booking;

import com.example.case_study_m4_team1.entity.Shift;
import com.example.case_study_m4_team1.repository.booking.IShiftRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService implements IShiftService {
    @Autowired
    private IShiftRepo shiftRepo;

    @Override
    public List<Shift>  getAllShifts() {
        return shiftRepo.findAllByOrderByStartTimeAsc();
    }
}
