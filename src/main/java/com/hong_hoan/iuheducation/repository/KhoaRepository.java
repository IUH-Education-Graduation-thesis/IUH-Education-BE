package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.Khoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhoaRepository extends JpaRepository<Khoa, Long> {

    Page<Khoa> findById(long id, Pageable pageable);

    Page<Khoa> findByKhoa(int khoa, Pageable pageable);

}