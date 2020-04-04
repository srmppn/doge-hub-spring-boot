package com.example.demo.services;

import java.util.Optional;

import com.example.demo.entities.Account;
import com.example.demo.services.exception.UsernameInflictException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpAccountService {
    @Autowired
    AccountService accountService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void SignUpAccount(Account account) throws UsernameInflictException{
        Optional<Account> existAccount = this.accountService.getAccountByName(account.getUserName());
        if(existAccount.isPresent()){
            throw new UsernameInflictException("This username is exist already.");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setNuggerPoint(0L);
        this.accountService.saveAccount(account);
    }
}