package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MonHocRepository extends JpaRepository<MonHoc, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mon_hoc WHERE (COALESCE(?1) IS NULL OR id IN (?1))", nativeQuery = true)
    void xoaMonHocs(List<Long> ids);
}