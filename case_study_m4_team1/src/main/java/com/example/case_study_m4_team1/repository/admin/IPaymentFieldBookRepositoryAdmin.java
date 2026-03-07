package com.example.case_study_m4_team1.repository.admin;

import com.example.case_study_m4_team1.dto.PaymentFieldBookDto;
import com.example.case_study_m4_team1.entity.PaymentFieldBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPaymentFieldBookRepositoryAdmin extends JpaRepository<PaymentFieldBook,Long> {
    @Query(value = """
        SELECT pf.id as id,
                u.name AS user,
               f.name AS field,
               fb.date_book AS date,
               f.price AS price,
               p.pay_type AS type,
               pf.status AS status
        FROM payment_field_book pf
        JOIN field_book fb ON pf.field_book_id = fb.id
        JOIN fields f ON fb.field_id = f.id
        JOIN users u ON fb.user_id = u.id
        JOIN pay p ON p.id = pf.pay_id
        WHERE (:statusStr IS NULL OR pf.status = :statusStr)
          AND (:searchUser IS NULL OR u.name LIKE :searchUser)
          AND (:searchField IS NULL OR f.name LIKE :searchField)
    """,
            countQuery = """
        SELECT COUNT(*)
        FROM payment_field_book pf
        JOIN field_book fb ON pf.field_book_id = fb.id
        JOIN fields f ON fb.field_id = f.id
        JOIN users u ON fb.user_id = u.id
        JOIN pay p ON p.id = pf.pay_id
        WHERE (:statusStr IS NULL OR pf.status = :statusStr)
          AND (:searchUser IS NULL OR u.name LIKE :searchUser)
          AND (:searchField IS NULL OR f.name LIKE :searchField)
    """,
            nativeQuery = true)
    Page<PaymentFieldBookDto> searchPayment(
            @Param("searchUser") String user,
            @Param("searchField") String field,
            @Param("statusStr") String statusStr,
            Pageable pageable
    );


    @Query(value = """
        select *
        from payment_field_book
        where field_book_id = :bookingId
        """, nativeQuery = true)
    PaymentFieldBook findByBookingId(@Param("bookingId") long bookingId);

    @Query(value = """
    select count(*)
    from payment_field_book
    where field_book_id = :bookingId
""", nativeQuery = true)
    int existsPayment(@Param("bookingId") long bookingId);


    @Query(value = """
    SELECT pf.id as id,
           u.name AS user,
           f.name AS field,
           fb.date_book AS date,
           f.price AS price,
           p.pay_type AS type,
           pf.status AS status
    FROM payment_field_book pf
    JOIN field_book fb ON pf.field_book_id = fb.id
    JOIN fields f ON fb.field_id = f.id
    JOIN users u ON fb.user_id = u.id
    JOIN pay p ON p.id = pf.pay_id
    WHERE fb.user_id = :userId
    ORDER BY fb.date_book DESC
""", nativeQuery = true)
    List<PaymentFieldBookDto> findByUserId(@Param("userId") long userId);

}
