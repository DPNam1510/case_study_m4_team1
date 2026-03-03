package com.example.case_study_m4_team1.rest_controller.admin;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.service.admin.field_book.IFieldBookServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/field_books")
public class AdminFieldBookController {
    @Autowired
    private IFieldBookServiceAdmin fieldBookServiceAdmin;

    @GetMapping("")
    public ResponseEntity<List<FieldBook>> getAllFieldBooking(){
        return ResponseEntity.ok(fieldBookServiceAdmin.findAll());
    }

    @PatchMapping("{id}/approve")
    public ResponseEntity<String> approveBooking(@PathVariable long id) {
        fieldBookServiceAdmin.approveBooking(id);
        return ResponseEntity.ok("Booking approved");
    }

}
