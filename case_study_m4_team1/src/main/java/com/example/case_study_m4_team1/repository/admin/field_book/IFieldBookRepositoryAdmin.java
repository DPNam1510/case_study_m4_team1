package com.example.case_study_m4_team1.repository.admin.field_book;

import com.example.case_study_m4_team1.entity.FieldBook;
import com.example.case_study_m4_team1.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface IFieldBookRepositoryAdmin extends JpaRepository<FieldBook,Long> {

    @Query(value = "SELECT fb.* FROM field_book fb " +
            "JOIN users u ON fb.user_id = u.id " +
            "JOIN fields f ON fb.field_id = f.id " +
            "WHERE fb.status = :status " +
            "AND (:searchUser IS NULL OR u.name LIKE :searchUser) " +
            "AND (:searchField IS NULL OR f.name LIKE :searchField) " +
            "AND (:searchDate IS NULL OR fb.date_book = :searchDate)",
            countQuery = "SELECT COUNT(*) FROM field_book fb " +
                    "JOIN users u ON fb.user_id = u.id " +
                    "JOIN fields f ON fb.field_id = f.id " +
                    "WHERE fb.status = :status " +
                    "AND (:searchUser IS NULL OR u.name LIKE :searchUser) " +
                    "AND (:searchField IS NULL OR f.name LIKE :searchField) " +
                    "AND (:searchDate IS NULL OR fb.date_book = :searchDate)",
            nativeQuery = true)
    Page<FieldBook> searchApprove(@Param("searchUser") String user,
                                  @Param("searchField") String field,
                                  @Param("searchDate") LocalDate date,
                                  @Param("status") String status,
                                  Pageable pageable);


    @Query(value = "SELECT fb.* FROM field_book fb " +
            "JOIN users u ON fb.user_id = u.id " +
            "JOIN fields f ON fb.field_id = f.id " +
            "WHERE fb.status = :status " +
            "AND (:searchUser IS NULL OR u.name LIKE :searchUser) " +
            "AND (:searchField IS NULL OR f.name LIKE :searchField) " +
            "AND (:searchDate IS NULL OR fb.date_book = :searchDate)",
            countQuery = "SELECT COUNT(*) FROM field_book fb " +
                    "JOIN users u ON fb.user_id = u.id " +
                    "JOIN fields f ON fb.field_id = f.id " +
                    "WHERE fb.status = :status " +
                    "AND (:searchUser IS NULL OR u.name LIKE :searchUser) " +
                    "AND (:searchField IS NULL OR f.name LIKE :searchField) " +
                    "AND (:searchDate IS NULL OR fb.date_book = :searchDate)",
            nativeQuery = true)
    Page<FieldBook> searchPending(
            @Param("searchUser") String user,
            @Param("searchField") String field,
            @Param("searchDate") LocalDate date,
            @Param("status") String status,      // nhận String, ví dụ 'PENDING'
            Pageable pageable);

    @Query(value = "SELECT fb.* FROM field_book fb " +
            "JOIN users u ON fb.user_id = u.id " +
            "JOIN fields f ON fb.field_id = f.id " +
            "WHERE fb.status = :status " +
            "AND (:searchUser IS NULL OR u.name LIKE :searchUser) " +
            "AND (:searchField IS NULL OR f.name LIKE :searchField) " +
            "AND (:searchDate IS NULL OR fb.date_book = :searchDate)",
            countQuery = "SELECT COUNT(*) FROM field_book fb " +
                    "JOIN users u ON fb.user_id = u.id " +
                    "JOIN fields f ON fb.field_id = f.id " +
                    "WHERE fb.status = :status " +
                    "AND (:searchUser IS NULL OR u.name LIKE :searchUser) " +
                    "AND (:searchField IS NULL OR f.name LIKE :searchField) " +
                    "AND (:searchDate IS NULL OR fb.date_book = :searchDate)",
            nativeQuery = true)
    Page<FieldBook> searchCanceled(
            @Param("searchUser") String user,
            @Param("searchField") String field,
            @Param("searchDate") LocalDate date,
            @Param("status") String status,      // nhận String, ví dụ 'CANCELED'
            Pageable pageable);

    boolean existsByFieldIdAndDateBookAndShiftIdAndStatusAndIdNot(
            int fieldId,
            LocalDate dateBook,
            int shiftId,
            BookingStatus status,
            long id
    );

    @Query(value = """
        select fb.*
        from field_book fb
        join users u on fb.user_id = u.id
        join fields f on fb.field_id = f.id
        where fb.status = :status
        and (:searchUser is null or u.name like :searchUser)
        and (:searchField is null or f.name like :searchField)
        """,
            countQuery = """
        select count(*)
        from field_book fb
        join users u on fb.user_id = u.id
        join fields f on fb.field_id = f.id
        where fb.status = :status
        and (:searchUser is null or u.name like :searchUser)
        and (:searchField is null or f.name like :searchField)
        """,
            nativeQuery = true)
    Page<FieldBook> findApprovedBookingForPayment(
            @Param("searchUser") String user,
            @Param("searchField") String field,
            @Param("status") BookingStatus status,
            Pageable pageable
    );

}
