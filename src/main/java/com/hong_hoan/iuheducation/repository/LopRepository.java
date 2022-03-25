package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.Lop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LopRepository extends JpaRepository<Lop, Long> {

    @Override
    Page<Lop> findAll(Pageable pageable);

    @Query(value = "SELECT l.* FROM  lop l " +
            "JOIN chuyen_nganh cn ON l.chuyen_nganh_id = cn.id " +
            "WHERE (:id IS NULL OR l.id = :id) " +
            "AND (:tenLop IS NULL OR l.ten like :tenLop) " +
            "AND (COALESCE(:khoaHocId) IS NULL OR l.khoa_id in (:khoaHocId)) " +
            "AND (COALESCE(:chuyenNganhId) IS NULL OR l.chuyen_nganh_id in (:chuyenNganhId)) " +
            "AND (COALESCE(:khoaVienId) IS NULL OR cn.khoa_vien_id in (:khoaVienId))", nativeQuery = true)
    @Transactional
    Page<Lop> findLopByFilter(@Param("id") String id, @Param("tenLop") String name, @Param("khoaHocId") List<Long> listIdKhoaHoc, @Param("chuyenNganhId") List<Long> listIdChuyenNganh, @Param("khoaVienId") List<Long> listIdKhoaVien, Pageable pageable);
}