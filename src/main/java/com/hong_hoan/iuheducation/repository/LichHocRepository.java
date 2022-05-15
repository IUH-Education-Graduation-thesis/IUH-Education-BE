package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.LichHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface LichHocRepository extends JpaRepository<LichHoc, Long> {

    @Query(value = "SELECT lh.* FROM lich_hoc lh\n" +
            "join lop_hoc_phan lhp ON lh.lop_hoc_phan_id = lhp.id\n" +
            "Join hoc_ky_normal hkn on hkn.id = lhp.hoc_ky_normal_id\n" +
            "WHERE hkn.id = ?1 \n" +
            "and (ISNULL(lh.nhom_thuc_hanh) or lh.nhom_thuc_hanh = ?2)\n" +
            "and lhp.id = ?3", nativeQuery = true)
    List<LichHoc> getListLichHocByLopHocPhanAndNhomThucHanhAndHocKy(Long hocKyNormalId, Integer nhomThucHanh, Long lopHocPhanId);

    @Query(value = "SELECT lh.* FROM lich_hoc lh\n" +
            "join lop_hoc_phan lhp ON lh.lop_hoc_phan_id = lhp.id\n" +
            "Join hoc_ky_normal hkn on hkn.id = lhp.hoc_ky_normal_id\n" +
            "join sinh_vien_lop_hoc_phan svlhp on svlhp.lop_hoc_phan_id = lhp.id\n" +
            "WHERE svlhp.sinh_vien_id = ?1\n" +
            "and (svlhp.nhom_thuc_hanh = lh.nhom_thuc_hanh or ISNULL(lh.nhom_thuc_hanh)) \n" +
            "and hkn.id = ?2", nativeQuery = true)
    List<LichHoc> getListLichHocCuaHocSinhDaDangKyTrongHocKy(Long sinhVienId, Long hocKyNormalId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM lich_hoc lh WHERE lh.id IN (?1)", nativeQuery = true)
    void xoaLichHocs(List<Long> ids);

    @Query(value = "SELECT * FROM lich_hoc lh " +
            "Join lop_hoc_phan lhp on lhp.id = lh.lop_hoc_phan_id " +
            "JOIN sinh_vien_lop_hoc_phan svlhp on svlhp.lop_hoc_phan_id = lhp.id " +
            "JOIN sinh_vien sv on sv.id = svlhp.sinh_vien_id " +
            "where sv.ma_sinh_vien = ?1 " +
            "and ?2 BETWEEN lh.thoi_gian_bat_dau AND ADDDATE(lh.thoi_gian_bat_dau, INTERVAL (7 * lh.lap_lai) DAY) " +
            "and ?2 BETWEEN ?3 AND ?4", nativeQuery = true)
    List<LichHoc> getListLichHocOfSinhVienWithWeek(String maSinhVien, Date dateInWeek, Date firstDateOfWeek, Date lastDateOfWeek);
}