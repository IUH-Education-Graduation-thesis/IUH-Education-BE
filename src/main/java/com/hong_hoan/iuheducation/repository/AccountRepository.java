package com.hong_hoan.iuheducation.repository;

import com.hong_hoan.iuheducation.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserName(String userName);

    boolean existsByUserName(String userName);

}