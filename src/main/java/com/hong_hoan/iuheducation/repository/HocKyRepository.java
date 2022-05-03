package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.HocKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HocKyRepository extends JpaRepository<HocKy, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM hoc_ky WHERE (COALESCE(?1) IS NULL OR id IN (?1))", nativeQuery = true)
    void xoaHocKies(List<Long> ids);
}