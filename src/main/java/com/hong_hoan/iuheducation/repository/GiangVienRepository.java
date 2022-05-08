package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.GiangVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GiangVienRepository extends JpaRepository<GiangVien, Long> {
    @Override
    Page<GiangVien> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM giang_vien gv " +
            "JOIN chuyen_nganh cn on cn.id = gv.chuyen_nganh_id " +
            "JOIN khoa_vien kv on kv.id = cn.khoa_vien_id " +
            "WHERE ((cn.khoa_vien_id in (?1)) OR COALESCE(?1) IS NULL) " +
            "AND (CONCAT(gv.ho_ten_dem, ' ', gv.ten) LIKE ?2 OR ?2 IS NULL) " +
            "AND (?3 = gv.id OR ?3 IS NULL)", nativeQuery = true)
    @Transactional
    Page<GiangVien> getGiangVienByNameAndKhoaVienId(Collection<Long> listIdKhoaVien, String name, Long id,Pageable pageable);

    @Query(value = "SELECT * FROM giang_vien gv " +
            "JOIN chuyen_nganh cn on cn.id = gv.chuyen_nganh_id " +
            "JOIN khoa_vien kv on kv.id = cn.khoa_vien_id " +
            "WHERE ((cn.khoa_vien_id in (?1)) OR COALESCE(?1) IS NULL) " +
            "AND (CONCAT(gv.ho_ten_dem, ' ', gv.ten) LIKE ?2 OR ?2 IS NULL) " +
            "AND (?3 = gv.id OR ?3 IS NULL)", nativeQuery = true)
    @Transactional
    List<GiangVien> getGiangVienByNameAndKhoaVienId(Collection<Long> listIdKhoaVien, String name, Long id);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM giang_vien WHERE (COALESCE(?1) IS NULL OR id IN (?1))", nativeQuery = true)
    void xoaGiangViens(List<Long> ids);
}