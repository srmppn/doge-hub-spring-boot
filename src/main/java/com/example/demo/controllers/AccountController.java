package com.example.demo.controllers;

import java.util.Optional;

import javax.validation.Valid;

import com.example.demo.entities.Account;
import com.example.demo.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class AccountController {
    @Autowired
    private AccountService accountService;
    
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAccounts(){
        return new ResponseEntity<>(this.accountService.getAllAccount(), HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getProfile(Authentication authentication){
        Optional<Account> account = this.accountService.getAccountByName(authentication.getName());
        if(account.isPresent()){
            return new ResponseEntity<>(account.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccountByName(@PathVariable("username") String username){
        Optional<Account> account = this.accountService.getAccountByName(username);
        if(account.isPresent()){
            return new ResponseEntity<>(account.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PreAuthorize("#account.getUserName() == authentication.principal.username or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> putAccount(Account account){
        Optional<Account> targetAccount = this.accountService.getAccountById(account.getAccountId());
        if(targetAccount.isPresent()){
            this.accountService.saveAccount(targetAccount.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}