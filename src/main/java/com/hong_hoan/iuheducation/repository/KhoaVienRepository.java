package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.KhoaVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhoaVienRepository extends JpaRepository<KhoaVien, Long> {
    Page<KhoaVien> findByTenContaining(String ten, Pageable pageable);
}