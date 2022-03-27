package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.ChuyenNganh;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChuyenNganhRepository extends JpaRepository<ChuyenNganh, Long> {
    @Query(value = "SELECT * FROM chuyen_nganh cn " +
            "WHERE (:id IS NULL OR cn.id = :id) " +
            "AND (COALESCE(:khoaVienIds) IS NULL OR cn.khoa_vien_id IN (:khoaVienIds))", nativeQuery = true)
    @Transactional
    List<ChuyenNganh> findChuyenNGanhWithFilter(@Param("id") String id, @Param("khoaVienIds") List<Long> khoaVienIds);

}