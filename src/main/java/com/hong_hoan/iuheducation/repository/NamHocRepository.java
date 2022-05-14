package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.NamHoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NamHocRepository extends JpaRepository<NamHoc, Long> {
    @Query(value = "SELECT * FROM nam_hoc nh " +
            "WHERE (?1 IS NULL OR nh.id = ?1) " +
            "AND (?2 IS NULL OR ?3 IS NULL OR nh.nam_bat_dau BETWEEN ?2 and ?3) " +
            "AND (?2 IS NULL OR ?3 IS NULL OR nh.nam_ket_thuc  BETWEEN ?2 and ?3)", nativeQuery = true)
    List<NamHoc> filterNamHoc(Long id, Integer fromDate, Integer toDate);

    @Query(value = "SELECT * FROM nam_hoc nh " +
            "WHERE (?1 IS NULL OR nh.id = ?1) " +
            "AND (?2 IS NULL OR ?3 IS NULL OR nh.nam_bat_dau BETWEEN ?2 and ?3) " +
            "AND (?2 IS NULL OR ?3 IS NULL OR nh.nam_ket_thuc  BETWEEN ?2 and ?3)", nativeQuery = true)
    Page<NamHoc> filterNamHoc(Long id, Integer fromDate, Integer toDate, Pageable pageable);

}