package com.example.demo.repositories;

import java.util.Optional;

import com.example.demo.entities.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByUserName(String userName);
}