package com.example.case_study_m4_team1.repository.admin.field_book;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IFieldBookRepositoryAdmin extends JpaRepository<FieldBook,Long> {
    List<FieldBook> findByStatus(BookingStatus status);

    List<FieldBook> findByDateBook(LocalDate date);
    boolean existsByFieldIdAndDateBookAndShiftIdAndStatusAndIdNot(
            int fieldId,
            LocalDate dateBook,
            int shiftId,
            BookingStatus status,
            long id
    );
}
