package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.LopHocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, Long> {
    @Query(value = "SELECT * FROM lop_hoc_phan lhp WHERE (:id IS NULL OR lhp.id = :id)", nativeQuery = true)
    @Transactional
    List<LopHocPhan> getListLopHocPhanWithFilter(@Param("id") String id);
}