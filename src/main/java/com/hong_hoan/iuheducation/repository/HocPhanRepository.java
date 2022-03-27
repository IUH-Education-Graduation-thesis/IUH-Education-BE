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
    List<HocPhan> filterHocPhan(@Param("id") long id, @Param("maHocPhan") String maHocPhan, @Param("namHocIds") List<Long> namHocIds, @Param("hocKyIds") List<Long> hocKyIds, @Param("monHocIds") List<Long> monHocIds, @Param("khoaVienIds") List<Long> khoaVienIds);

    @Query(value = "SELECT hp.* FROM hoc_phan hp " + "JOIN hoc_ky hk ON hk.id = hp.hoc_ky_id " + "JOIN mon_hoc mh ON hp.mon_hoc_id = mh.id " + "WHERE (:id IS NULL OR hp.id = :id) " + "AND (:maHocPhan IS NULL OR hp.ma_hoc_phan like :maHocPhan) " + "AND (COALESCE(:namHocIds) IS NULL OR hk.nam_hoc_id IN (:namHocIds)) " + "AND (COALESCE(:hocKyIds) IS NULL OR hk.id IN (:hocKyIds)) " + "AND (COALESCE(:monHocIds) IS NULL OR hp.mon_hoc_id IN (:monHocIds)) " + "AND (COALESCE(:khoaVienIds) IS NULL OR mh.khoa_vien_id IN (:khoaVienIds))", nativeQuery = true)
    @Transactional
    Page<HocPhan> filterHocPhan(@Param("id") long id, @Param("maHocPhan") String maHocPhan, @Param("namHocIds") List<Long> namHocIds, @Param("hocKyIds") List<Long> hocKyIds, @Param("monHocIds") List<Long> monHocIds, @Param("khoaVienIds") List<Long> khoaVienIds, Pageable pageable);

}