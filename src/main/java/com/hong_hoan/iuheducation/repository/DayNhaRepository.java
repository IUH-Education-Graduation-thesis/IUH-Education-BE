package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.DayNha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DayNhaRepository extends JpaRepository<DayNha, Long> {
    List<DayNha> findByTenDayNhaContaining(String tenDayNha);

}