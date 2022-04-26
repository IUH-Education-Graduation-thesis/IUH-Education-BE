package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.Khoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KhoaRepository extends JpaRepository<Khoa, Long> {

    Page<Khoa> findById(long id, Pageable pageable);

    Page<Khoa> findByKhoa(int khoa, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM khoa WHERE (COALESCE(?1) IS NULL OR id IN (?1))", nativeQuery = true)
    void xoaKhoaHocs(List<Long> ids);

}