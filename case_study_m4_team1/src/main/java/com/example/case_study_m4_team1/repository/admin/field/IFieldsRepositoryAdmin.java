package com.example.case_study_m4_team1.repository.admin.field;

import com.example.case_study_m4_team1.entity.Fields;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IFieldsRepositoryAdmin extends JpaRepository<Fields,Integer> {
    Page<Fields> findByNameContaining(String name, Pageable pageable);

    @Query(value = "select * from fields " +
            "where name like :searchName " +
            "and price = :searchPrice", nativeQuery = true)
    Page<Fields> search(@Param("searchName") String name,
                        @Param("searchPrice") double price,
                        Pageable pageable);


}
