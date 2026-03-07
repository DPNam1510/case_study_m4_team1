package com.example.case_study_m4_team1.repository.booking;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IFieldBookRepo extends JpaRepository<FieldBook,Long> {
    // kiểm tra trùng lịch sân
    boolean existsByField_IdAndShift_IdAndDateBookAndStatusIn(
            int fieldId,
            int shiftId,
            LocalDate dateBook,
            List<BookingStatus> statuses
    );

    // Update(kiểm tra trùng nhưng bỏ qua chính nó)
    boolean existsByField_IdAndShift_IdAndDateBookAndStatusInAndIdNot(
            int fieldId,
            int shiftId,
            LocalDate dateBook,
            List<BookingStatus> statuses,
            long id
    );

    // Kiểm tra booking của user trong 1 ca (tránh trùng lịch)
    boolean existsByUser_IdAndShift_IdAndDateBookAndStatusIn(
            long userId,
            int shiftId,
            LocalDate dateBook,
            List<BookingStatus> statuses
    );

    // xem chi tiết bôking
    @Query("""
       SELECT fb FROM FieldBook fb
       JOIN FETCH fb.user
       JOIN FETCH fb.field
       JOIN FETCH fb.shift
       WHERE fb.id = :id
       """)
    Optional<FieldBook> findDetailById(
            @Param("id") Long id);

    // Hủy sân trước 1 tiếng
    @Query("""
       SELECT fb FROM FieldBook fb
       JOIN FETCH fb.shift
       WHERE fb.id = :id
       AND fb.user.id = :userId
       AND fb.status IN :status
       """)
    Optional<FieldBook> findCancelableBooking(
            @Param("id") Long id,
            @Param("userId") Long userId,
            @Param("status") List<BookingStatus> statuses
    );

    // xem lịch sử đạt sân, phân trang
    Page<FieldBook> findAllByUser_IdOrderByDateBookDesc(
            Long userId, Pageable pageable);
}
