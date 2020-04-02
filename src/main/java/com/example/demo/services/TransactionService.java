package com.example.demo.services;

import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.services.exception.InsufficientNuggerPointException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    AccountRepository accountRepository;
    public TransactionService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public void transferNuggerPointToAccount(Account senderAccount, Account receiverAccount,Long amountOfNugger) throws InsufficientNuggerPointException{ 
        if(senderAccount != null && receiverAccount != null){
            // get both account balance
            Long senderCurrentNugger = senderAccount.getNuggerPoint();
            Long receiverCurrentNugger = receiverAccount.getNuggerPoint();

            if(senderCurrentNugger < amountOfNugger) {
                // i have no idea what to do
                throw new InsufficientNuggerPointException("Sorry, insufficeint nugger point.");
            }

            senderAccount.setNuggerPoint(senderCurrentNugger - amountOfNugger);
            receiverAccount.setNuggerPoint(receiverCurrentNugger + amountOfNugger);
            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);
        }
    }
}