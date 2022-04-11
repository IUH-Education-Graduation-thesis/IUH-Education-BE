package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.HocPhan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HocPhanRepository extends JpaRepository<HocPhan, Long> {
    @Query(value = "SELECT hp.* FROM hoc_phan hp " + "JOIN hoc_ky hk ON hk.id = hp.hoc_ky_id " + "JOIN mon_hoc mh ON hp.mon_hoc_id = mh.id " + "WHERE (:id IS NULL OR hp.id = :id) " + "AND (:maHocPhan IS NULL OR hp.ma_hoc_phan like :maHocPhan) " + "AND (COALESCE(:namHocIds) IS NULL OR hk.nam_hoc_id IN (:namHocIds)) " + "AND (COALESCE(:hocKyIds) IS NULL OR hk.id IN (:hocKyIds)) " + "AND (COALESCE(:monHocIds) IS NULL OR hp.mon_hoc_id IN (:monHocIds)) " + "AND (COALESCE(:khoaVienIds) IS NULL OR mh.khoa_vien_id IN (:khoaVienIds))", nativeQuery = true)
    @Transactional
    List<HocPhan> filterHocPhan(@Param("id") String id, @Param("maHocPhan") String maHocPhan, @Param("namHocIds") List<Long> namHocIds, @Param("hocKyIds") List<Long> hocKyIds, @Param("monHocIds") List<Long> monHocIds, @Param("khoaVienIds") List<Long> khoaVienIds);

    @Query(value = "SELECT hp.* FROM hoc_phan hp " + "JOIN hoc_ky hk ON hk.id = hp.hoc_ky_id " + "JOIN mon_hoc mh ON hp.mon_hoc_id = mh.id " + "WHERE (:id IS NULL OR hp.id = :id) " + "AND (:maHocPhan IS NULL OR hp.ma_hoc_phan like :maHocPhan) " + "AND (COALESCE(:namHocIds) IS NULL OR hk.nam_hoc_id IN (:namHocIds)) " + "AND (COALESCE(:hocKyIds) IS NULL OR hk.id IN (:hocKyIds)) " + "AND (COALESCE(:monHocIds) IS NULL OR hp.mon_hoc_id IN (:monHocIds)) " + "AND (COALESCE(:khoaVienIds) IS NULL OR mh.khoa_vien_id IN (:khoaVienIds))", nativeQuery = true)
    @Transactional
    Page<HocPhan> filterHocPhan(@Param("id") String id, @Param("maHocPhan") String maHocPhan, @Param("namHocIds") List<Long> namHocIds, @Param("hocKyIds") List<Long> hocKyIds, @Param("monHocIds") List<Long> monHocIds, @Param("khoaVienIds") List<Long> khoaVienIds, Pageable pageable);

    @Query(value = "SELECT * FROM hoc_phan hp WHERE hp.hoc_ky_id = ?1 AND hp.id not in(" +
            "SELECT lhp.hoc_phan_id as hocPhanId FROM sinh_vien_lop_hoc_phan svlhp " +
            "join lop_hoc_phan lhp ON lhp.id = svlhp.lop_hoc_phan_id " +
            "WHERE svlhp.sinh_vien_id = ?2 " +
            ")", nativeQuery = true)
    List<HocPhan> getListHocPhanByHocKyForDKHP(Long hocPhanId, Long sinhVienId);


    @Query(value = "SELECT hp.* FROM hoc_phan hp " +
            "JOIN hoc_ky hk ON hk.id = hp.hoc_ky_id " +
            "JOIN khoa k on k.id = hk.khoa_id " +
            "WHERE k.id = ?1 AND hk.thu_tu <= ?2 AND hp.id not in( " +
            "SELECT lhp.hoc_phan_id as hocPhanId FROM sinh_vien_lop_hoc_phan svlhp " +
            "join lop_hoc_phan lhp ON lhp.id = svlhp.lop_hoc_phan_id " +
            "WHERE svlhp.sinh_vien_id = ?3 " +
            ")", nativeQuery = true)
    List<HocPhan> getListHocPhanHocMoi(Long khoaId, Integer thuTuHocKy, Long maSinhVien);
}