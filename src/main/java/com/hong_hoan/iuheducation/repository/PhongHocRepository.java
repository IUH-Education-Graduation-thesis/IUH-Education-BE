package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.PhongHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhongHocRepository extends JpaRepository<PhongHoc, Long> {
    @Query("select p from PhongHoc p where p.dayNha.id = ?1")
    List<PhongHoc> findByDayNhaId(Long id);

}