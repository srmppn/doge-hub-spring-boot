package com.example.demo.controllers;

import java.util.Optional;

import com.example.demo.entities.Account;
import com.example.demo.entities.Transaction;
import com.example.demo.services.AccountService;
import com.example.demo.services.TransactionService;
import com.example.demo.services.exception.InsufficientNuggerPointException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class TransactionController {
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    public TransactionController(){

    }
    
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<?> sendNuggerPoint(@RequestBody Transaction transaction){
        System.out.println("FUCKING DID IT ALREADY");
        Optional<Account> sender = accountService.getAccountById(transaction.getSenderId());
        Optional<Account> receiver = accountService.getAccountById(transaction.getReceiverId());
        if(sender.isPresent()&&receiver.isPresent()){
            try {
                transactionService.transferNuggerPointToAccount(sender.get(), receiver.get(), transaction.getAmoutOfNugger());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            catch(InsufficientNuggerPointException e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}