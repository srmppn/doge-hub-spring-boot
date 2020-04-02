package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    public List<Account> getAllAccount(){
        return this.repository.findAll();
    }

    public Optional<Account> getAccountById(Long id){
        return this.repository.findById(id);
    }

    public Optional<Account> getAccountByName(String name){
        return this.repository.findByUserName(name);
    }

    public Account saveAccount(Account account){
        return this.repository.save(account);
    }
}