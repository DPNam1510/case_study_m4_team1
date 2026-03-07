package com.example.case_study_m4_team1.repository;

import com.example.case_study_m4_team1.dto.PaymentRegisterDto;
import com.example.case_study_m4_team1.entity.PaymentRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPaymentRegisterRepository extends JpaRepository<PaymentRegister,Long> {
    @Query(value = "select pr.id as id, sc.class_name as className, u.name as user,f.name as field,t.name as teacher,\n" +
            "cr.date_register as date, sc.price as price,p.pay_type as type,\n" +
            "pr.status as status\n" +
            "from payment_register pr\n" +
            "join pay p on pr.pay_id = p.id\n" +
            "join class_register cr on pr.register_id = cr.id\n" +
            "join users u on cr.user_id = u.id\n" +
            "join study_schedule sc on cr.schedule_id = sc.id\n" +
            "join teacher t on sc.teacher_id = t.id\n" +
            "join fields f on sc.field_id = f.id\n" +
            "join shift s on sc.shift_id = s.id\n" +
            "where sc.status_class = 'OPEN' " +
            "and (:searchClassName is null or sc.class_name like :searchClassName) " +
            "and (:searchUser is null or u.name like :searchUser) " +
            "and (:searchTeacher is null or t.name like :searchTeacher) " +
            "and (:statusStr is null or pr.status = :statusStr) ",
            countQuery = "select count(*)" +
                    "from payment_register pr\n" +
                    "join pay p on pr.pay_id = p.id\n" +
                    "join class_register cr on pr.register_id = cr.id\n" +
                    "join users u on cr.user_id = u.id\n" +
                    "join study_schedule sc on cr.schedule_id = sc.id\n" +
                    "join teacher t on sc.teacher_id = t.id\n" +
                    "join fields f on sc.field_id = f.id\n" +
                    "join shift s on sc.shift_id = s.id\n" +
                    "where sc.status_class = 'OPEN' " +
                    "and (:searchClassName is null or sc.class_name like :searchClassName) " +
                    "and (:searchUser is null or u.name like :searchUser) " +
                    "and (:searchTeacher is null or t.name like :searchTeacher) " +
                    "and (:statusStr is null or pr.status = :statusStr) "
            , nativeQuery = true)
    Page<PaymentRegisterDto> searchPayment(@Param("searchClassName") String className,
                                           @Param("searchUser") String user,
                                           @Param("searchTeacher") String teacher,
                                           @Param("statusStr") String statusStr,
                                           Pageable pageable);


}
