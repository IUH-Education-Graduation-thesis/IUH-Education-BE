package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.HocKyNormal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface HocKyNormalRepository extends JpaRepository<HocKyNormal, Long> {
    @Query(value = "SELECT hkn.* FROM hoc_ky_normal hkn " +
            "JOIN nam_hoc nh on nh.id = hkn.nam_hoc_id " +
            "WHERE nh.nam_bat_dau >= YEAR(?1)", nativeQuery = true)
    List<HocKyNormal> getListHocKyOfSinhVien(Date ngayVaoTruong);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM hoc_ky_normal hkn WHERE (COALESCE(?1) IS NULL OR hkn.id IN (?1))", nativeQuery = true)
    void xoaHocKyNormals(List<Long> ids);
}