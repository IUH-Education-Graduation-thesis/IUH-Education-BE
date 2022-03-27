package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.NamHoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NamHocRepository extends JpaRepository<NamHoc, Long> {

    @Override
    Page<NamHoc> findAll(Pageable pageable);

    Page<NamHoc> findById(Long id, Pageable pageable);

    Page<NamHoc> findByNgayBatDauBetweenAndNgayKetThucBetween(Date ngayBatDauStart, Date ngayBatDauEnd, Date ngayKetThucStart, Date ngayKetThucEnd, Pageable pageable);

    @Query(value = "SELECT  * FROM  nam_hoc nh WHERE nh.id = ?1 LIMIT ?2 OFFSET ?3", nativeQuery = true)
    List<NamHoc> findByIdWithPagination(Long id, int limit, int offset);

    @Query(value = "SELECT  * FROM  nam_hoc nh LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<NamHoc> findAllWithPagination(int limit, int offset);

}