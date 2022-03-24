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

    @Query(value = "SELECT * FROM  giang_vien gv WHERE ((gv.khoa_vien_id in (?1)) OR COALESCE(?1) IS NULL) AND (CONCAT(gv.ho_ten_dem, ' ', gv.ten) LIKE ?2 OR ?2 IS NULL)", nativeQuery = true)
    @Transactional
    Page<GiangVien> getGiangVienByNameAndKhoaVienId(Collection<Long> listIdKhoaVien, String name, Pageable pageable);

}