package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.KhoaVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KhoaVienRepository extends JpaRepository<KhoaVien, Long> {
    Page<KhoaVien> findByTenContaining(String ten, Pageable pageable);

    @Query(value = "SELECT * FROM khoa_vien kv " +
            "WHERE (:id IS NULL OR kv.id = :id) " +
            "AND (:ten IS NULL OR kv.ten like :ten)", nativeQuery = true)
    @Transactional
    List<KhoaVien> findKhoaVienFilter(@Param("id") String id, @Param("ten") String ten);


    @Query(value = "SELECT * FROM khoa_vien kv " +
            "WHERE (:id IS NULL OR kv.id = :id) " +
            "AND (:ten IS NULL OR kv.ten like :ten)", nativeQuery = true)
    @Transactional
    Page<KhoaVien> findKhoaVienFilter(@Param("id") String id, @Param("ten") String ten, Pageable pageable);
}