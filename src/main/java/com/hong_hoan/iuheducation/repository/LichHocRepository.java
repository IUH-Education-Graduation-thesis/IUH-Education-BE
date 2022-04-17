package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.LichHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LichHocRepository extends JpaRepository<LichHoc, Long> {
    @Query(value = "SELECT * FROM lich_hoc lh " +
            "Join lop_hoc_phan lhp on lhp.id = lh.lop_hoc_phan_id " +
            "JOIN sinh_vien_lop_hoc_phan svlhp on svlhp.lop_hoc_phan_id = lhp.id " +
            "JOIN sinh_vien sv on sv.id = svlhp.sinh_vien_id " +
            "where sv.ma_sinh_vien = ?1 " +
            "and ?2 BETWEEN lh.thoi_gian_bat_dau AND ADDDATE(lh.thoi_gian_bat_dau, INTERVAL (7 * lh.lap_lai) DAY) " +
            "and ?2 BETWEEN ?3 AND ?4", nativeQuery = true)
    List<LichHoc> getListLichHocOfSinhVienWithWeek(String maSinhVien, Date dateInWeek, Date firstDateOfWeek, Date lastDateOfWeek);
}