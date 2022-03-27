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

    @Query(value = "SELECT l.* FROM `lop` l JOIN chuyen_nganh cn ON l.chuyen_nganh_id = cn.id WHERE (?1 IS NULL OR l.id = ?1) AND (?2 IS NULL OR l.ten like ?2) AND (COALESCE(?3) IS NULL OR l.khoa_id in (?3)) AND (COALESCE(?4) IS NULL OR l.chuyen_nganh_id in (?4)) AND (COALESCE(?5) IS NULL OR cn.khoa_vien_id in (?5))", nativeQuery = true)
    @Transactional
    Page<Lop> findLopByFilter(String id, String name, List<Long> listIdKhoaHoc, List<Long> listIdChuyenNganh, List<Long> listIdKhoaVien, Pageable pageable);
}