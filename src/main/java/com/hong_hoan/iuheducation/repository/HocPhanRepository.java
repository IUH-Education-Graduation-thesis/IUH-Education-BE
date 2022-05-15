package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.HocPhan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HocPhanRepository extends JpaRepository<HocPhan, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM hoc_phan WHERE (COALESCE(?1) IS NULL OR id IN (?1))", nativeQuery = true)
    void xoaHocPhans(List<Long> ids);

    @Query(value = "SELECT hp.* FROM hoc_phan hp " +
            "JOIN hoc_phan_hoc_ky hphk on hphk.hoc_phan_id = hp.id " +
            "JOIN hoc_ky hk on hk.id = hphk.hoc_ky_id " +
            "JOIN khoa k on k.id = hk.khoa_id " +
            "JOIN chuyen_nganh cn on cn.id = k.chuyen_nganh_id " +
            "JOIN khoa_vien kv on kv.id = cn.khoa_vien_id " +
            "Join mon_hoc mh on mh.id = hp.mon_hoc_id " +
            "WHERE (:id IS NULL OR hp.id = :id) " +
            "AND (:maHocPhan IS NULL OR hp.ma_hoc_phan like :maHocPhan) " +
            "AND (COALESCE(:hocKyIds) IS NULL OR hk.id IN (:hocKyIds)) " +
            "AND (COALESCE(:khoaIds) IS NULL OR k.id IN (:khoaIds)) " +
            "AND (COALESCE(:chuyenNganhIds) IS NULL OR cn.id IN (:chuyenNganhIds)) " +
            "AND (COALESCE(:khoaVienIds) IS NULL OR kv.id IN (:khoaVienIds)) " +
            "AND (COALESCE(:monHocIds) IS NULL OR mh.id IN (:monHocIds))", nativeQuery = true)
    @Transactional
    Page<HocPhan> filterHocPhan(@Param("id") Long id, @Param("maHocPhan") String maHocPhan, @Param("hocKyIds") List<Long> hocKyIds,
                                @Param("khoaIds") List<Long> khoaIds, @Param("chuyenNganhIds") List<Long> chuyenNganhIds,
                                @Param("khoaVienIds") List<Long> khoaVienIds, @Param("monHocIds") List<Long> monHocIds, Pageable pageable);

    @Query(value = "SELECT hp.* FROM hoc_phan hp " +
            "JOIN hoc_phan_hoc_ky hphk on hphk.hoc_phan_id = hp.id " +
            "JOIN hoc_ky hk on hk.id = hphk.hoc_ky_id " +
            "JOIN khoa k on k.id = hk.khoa_id " +
            "JOIN chuyen_nganh cn on cn.id = k.chuyen_nganh_id " +
            "JOIN khoa_vien kv on kv.id = cn.khoa_vien_id " +
            "Join mon_hoc mh on mh.id = hp.mon_hoc_id " +
            "WHERE (:id IS NULL OR hp.id = :id) " +
            "AND (:maHocPhan IS NULL OR hp.ma_hoc_phan like :maHocPhan) " +
            "AND (COALESCE(:hocKyIds) IS NULL OR hk.id IN (:hocKyIds)) " +
            "AND (COALESCE(:khoaIds) IS NULL OR k.id IN (:khoaIds)) " +
            "AND (COALESCE(:chuyenNganhIds) IS NULL OR cn.id IN (:chuyenNganhIds)) " +
            "AND (COALESCE(:khoaVienIds) IS NULL OR kv.id IN (:khoaVienIds)) " +
            "AND (COALESCE(:monHocIds) IS NULL OR mh.id IN (:monHocIds))", nativeQuery = true)
    @Transactional
    List<HocPhan> filterHocPhan(@Param("id") Long id, @Param("maHocPhan") String maHocPhan, @Param("hocKyIds") List<Long> hocKyIds,
                                @Param("khoaIds") List<Long> khoaIds, @Param("chuyenNganhIds") List<Long> chuyenNganhIds,
                                @Param("khoaVienIds") List<Long> khoaVienIds, @Param("monHocIds") List<Long> monHocIds);

    @Query(value = "SELECT * FROM hoc_phan hp WHERE hp.hoc_ky_id = ?1 AND hp.id not in(" +
            "SELECT lhp.hoc_phan_id as hocPhanId FROM sinh_vien_lop_hoc_phan svlhp " +
            "join lop_hoc_phan lhp ON lhp.id = svlhp.lop_hoc_phan_id " +
            "WHERE svlhp.sinh_vien_id = ?2 " +
            ")", nativeQuery = true)
    List<HocPhan> getListHocPhanByHocKyForDKHP(Long hocPhanId, Long sinhVienId);

    @Query(value = "SELECT hp.* FROM hoc_phan hp\n" +
            "JOIN lop_hoc_phan lhp on lhp.hoc_phan_id = hp.id\n" +
            "# check học phần đó học kỳ này có mở môn nào hay không\n" +
            "WHERE (\n" +
            "\tSELECT COUNT(*) as soLopHocPhan FROM lop_hoc_phan lhp2\n" +
            "\tWHERE lhp2.hoc_ky_normal_id = ?1 AND lhp2.id = lhp.id \n" +
            ") > 0\n" +
            "# check hoc phan do hoc sinh da học lần nào hay chưa\n" +
            "AND (\n" +
            "\tSELECT COUNT(*) as soSinhVienLopHocPhan FROM sinh_vien_lop_hoc_phan svlhp\n" +
            "\tJOIN lop_hoc_phan lhp3 ON lhp3.id = svlhp.lop_hoc_phan_id\n" +
            "\tWHERE svlhp.sinh_vien_id = ?2 AND lhp3.hoc_phan_id = hp.id\n" +
            ") < 1\n" +
            "# check hoc phan thuoc sinh vien\n" +
            "AND EXISTS (\n" +
            "\tSELECT hp.* FROM sinh_vien sv\n" +
            "\tJOIN lop l ON l.id = sv.lop_id\n" +
            "\tJOIN khoa k on k.id = l.khoa_id\n" +
            "\tJOIN hoc_ky hk on hk.khoa_id = k.id\n" +
            "\tJOIN hoc_phan_hoc_ky hphk ON hphk.hoc_ky_id = hk.id\n" +
            "\tJOIN hoc_phan hp on hphk.hoc_phan_id  = hp.id \n" +
            "\tWHERE sv.id = ?2\n" +
            ")\n" +
            "GROUP BY hp.id", nativeQuery = true)
    List<HocPhan> getListHocPhanForDangKyHocMoi(Long hocKyId, Long sinhVienId);

    @Query(value = "SELECT MAX(hp.id) as max_id FROM hoc_phan hp", nativeQuery = true)
    Integer getMaxIdExistInDB();

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