package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.LopHocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, Long> {
    @Query(value = "SELECT * FROM lop_hoc_phan lhp WHERE (:id IS NULL OR lhp.id = :id)", nativeQuery = true)
    @Transactional
    List<LopHocPhan> getListLopHocPhanWithFilter(@Param("id") String id);

    @Query(value = "SELECT lhp.* FROM hoc_phan hp " +
            "JOIN lop_hoc_phan lhp on lhp.hoc_phan_id = hp.id " +
            "JOIN sinh_vien_lop_hoc_phan svlhp on svlhp.lop_hoc_phan_id = lhp.id " +
            "JOIN sinh_vien sv on sv.id = svlhp.sinh_vien_id " +
            "JOIN hoc_ky_normal hkn on hkn.id = lhp.hoc_ky_normal_id " +
            "WHERE sv.id = ?1 AND hkn.id = ?2", nativeQuery = true)
    List<LopHocPhan> getListLopHocPhanDangKyByHocKyAndSinhVien(Long sinhVienId, Long hocKyId);

    @Query(value = "SELECT * FROM lop_hoc_phan lhp " +
            "WHERE lhp.hoc_phan_id = ?1 " +
            "AND lhp.hoc_ky_normal_id = ?2", nativeQuery = true)
    List<LopHocPhan> getLopHocPhanByLopHocPhanDangKy(Long hocPhanId, Long hocKyNormalId);
}