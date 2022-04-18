package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhan;
import com.hong_hoan.iuheducation.entity.SinhVienLopHocPhanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SinhVienLopHocPhanRepository extends JpaRepository<SinhVienLopHocPhan, SinhVienLopHocPhanId> {
    @Query(value = "SELECT svlhp.* from sinh_vien_lop_hoc_phan svlhp " +
            "JOIN lop_hoc_phan lhp ON lhp.id = svlhp.lop_hoc_phan_id " +
            "JOIN hoc_ky_normal hkn on hkn.id = lhp.hoc_ky_normal_id " +
            "where svlhp.sinh_vien_id = ?1 and hkn.id = ?2", nativeQuery = true)
    List<SinhVienLopHocPhan> getSinhVienLopHocPhansBySinhVienIdAndHocKyId(Long sinhVienId, Long hocKyNormalId);

}