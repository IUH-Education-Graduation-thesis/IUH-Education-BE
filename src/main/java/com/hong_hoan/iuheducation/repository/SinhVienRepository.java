package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.SinhVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SinhVienRepository extends JpaRepository<SinhVien, Long> {

    @Query(value = "SELECT sv.* FROM sinh_vien sv " +
            "JOIN sinh_vien_lop_hoc_phan svlhp on svlhp.sinh_vien_id = sv.id " +
            "WHERE svlhp.lop_hoc_phan_id  = ?1", nativeQuery = true)
    List<SinhVien> findSinhVienInLopHocPhan(Long lopHocPhanId);


    /**
     *  Cái hàm này sẽ tìm tất cả sinh viên của học phần mà sinh viên đó chưa có học hoặc đăng ký bất
     *  kỳ lớp học phần nào
     * @param hocPhanId học phần id
     * @return list sinh viên
     */
    @Query(value = "SELECT sv.* from sinh_vien sv\n" +
            "JOIN lop l on l.id = sv.lop_id\n" +
            "JOIN khoa k on k.id = l.khoa_id \n" +
            "JOIN hoc_ky hk on hk.khoa_id = k.id\n" +
            "JOIN hoc_phan_hoc_ky hphk on hphk.hoc_ky_id = hk.id\n" +
            "WHERE hphk.hoc_phan_id = ?1\n" +
            "AND (\n" +
            "\tselect COUNT(*) from hoc_phan hp\n" +
            "\tjoin lop_hoc_phan lhp on lhp.hoc_phan_id = hp.id\n" +
            "\tjoin sinh_vien_lop_hoc_phan svlhp on svlhp.lop_hoc_phan_id = lhp.id \n" +
            "\tWHERE hp.id = ?1 AND svlhp.sinh_vien_id = sv.id \n" +
            ") <= 0", nativeQuery = true)
    List<SinhVien> getListSinhOfHocPhanButNotYetRegistry(Long hocPhanId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sinh_vien WHERE (COALESCE(?1) IS NULL OR id IN (?1))", nativeQuery = true)
    void xoaSinhViens(List<Long> ids);

    @Query(value = "SELECT MAX(sv.id) as max_id  FROM sinh_vien sv", nativeQuery = true)
    Integer getMaxId();

    @Query(value = "SELECT sv.* FROM sinh_vien sv " +
            "JOIN lop l on l.id = sv.lop_id " +
            "JOIN khoa k on k.id = l.khoa_id " +
            "JOIN chuyen_nganh cn on cn.id = k.chuyen_nganh_id " +
            "WHERE (:id IS NULL OR sv.id = :id) " +
            "AND (:maSinhVien IS NULL OR sv.ma_sinh_vien like :maSinhVien) " +
            "AND (:tenSinhVien IS NULL OR CONCAT(sv.ho_ten_dem, ' ', sv.ten) like :tenSinhVien) " +
            "AND (COALESCE(:lopIds) IS NULL OR sv.lop_id IN (:lopIds)) " +
            "AND (COALESCE(:chuyenNganhIds) IS NULL OR k.chuyen_nganh_id IN (:chuyenNganhIds)) " +
            "AND (COALESCE(:khoaVienIds) IS NULL OR cn.khoa_vien_id IN (:khoaVienIds)) " +
            "AND (COALESCE(:khoaHocIds) IS NULL OR l.khoa_id IN (:khoaHocIds))", nativeQuery = true)
    @Transactional
    List<SinhVien> findListSinhVienFilter(@Param("id") String id, @Param("maSinhVien") String maSinhVien, @Param("tenSinhVien") String tenSinhVien, @Param("lopIds") List<Long> lopIds, @Param("chuyenNganhIds") List<Long> chuyenNganhIds, @Param("khoaVienIds") List<Long> khoaVienIds, @Param("khoaHocIds") List<Long> khoaHocIds);

    @Query(value = "SELECT sv.* FROM sinh_vien sv " +
            "JOIN lop l on l.id = sv.lop_id " +
            "JOIN khoa k on k.id = l.khoa_id " +
            "JOIN chuyen_nganh cn on cn.id = k.chuyen_nganh_id " +
            "WHERE (:id IS NULL OR sv.id = :id) " +
            "AND (:maSinhVien IS NULL OR sv.ma_sinh_vien like :maSinhVien) " +
            "AND (:tenSinhVien IS NULL OR CONCAT(sv.ho_ten_dem, ' ', sv.ten) like :tenSinhVien) " +
            "AND (COALESCE(:lopIds) IS NULL OR sv.lop_id IN (:lopIds)) " +
            "AND (COALESCE(:chuyenNganhIds) IS NULL OR k.chuyen_nganh_id IN (:chuyenNganhIds)) " +
            "AND (COALESCE(:khoaVienIds) IS NULL OR cn.khoa_vien_id IN (:khoaVienIds)) " +
            "AND (COALESCE(:khoaHocIds) IS NULL OR l.khoa_id IN (:khoaHocIds))", nativeQuery = true)
    @Transactional
    Page<SinhVien> findListSinhVienFilter(@Param("id") String id, @Param("maSinhVien") String maSinhVien, @Param("tenSinhVien") String tenSinhVien, @Param("lopIds") List<Long> lopIds, @Param("chuyenNganhIds") List<Long> chuyenNganhIds, @Param("khoaVienIds") List<Long> khoaVienIds, @Param("khoaHocIds") List<Long> khoaHocIds, Pageable pageable);


}