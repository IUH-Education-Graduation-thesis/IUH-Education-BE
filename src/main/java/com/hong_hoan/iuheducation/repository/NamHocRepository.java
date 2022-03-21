package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.NamHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface NamHocRepository extends JpaRepository<NamHoc, Long> {
    @Query(value = "SELECT * FROM nam_hoc nh WHERE IF(ISNULL(?1), NOT ISNULL(nh.id), nh.id = ?1)", nativeQuery = true)
    List<NamHoc> findByIdEquals(String id);

    List<NamHoc> findByNgayBatDauBetweenAndNgayKetThucBetween(Date ngayBatDauStart, Date ngayBatDauEnd, Date ngayKetThucStart, Date ngayKetThucEnd);

    List<NamHoc> findByNgayBatDauBetween(Date ngayBatDauStart, Date ngayBatDauEnd);




}