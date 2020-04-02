package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.entities.Account;
import com.example.demo.services.exception.InsufficientNuggerPointException;

import org.junit.Before;
import org.junit.Test;

public class TestTransactionService {

    private TransactionService transactionService;
    @Before
    public void setUp(){
        //this.transactionService = new TransactionService();
    }

    @Test
    public void transferBalanceToAccount_ShouldTransferSucessfully() throws Exception{
        // Arrange
        Account sender = new Account();
        sender.setNuggerPoint(100L);
        Account receiver = new Account();
        receiver.setNuggerPoint(100L);

        // Act
        transactionService.transferNuggerPointToAccount(sender, receiver, 10L);

        // Assert
        assertEquals(90, sender.getNuggerPoint(), "Should less than 100");
        assertEquals(110, receiver.getNuggerPoint(), "Should greater than 100");
    }
    @Test(expected = InsufficientNuggerPointException.class)
    public void transferNuggerPointToAccount_ShouldNotBeAbleToTransfer() throws Exception{
        // Arrange
        Account sender = new Account();
        sender.setNuggerPoint(0L);
        Account receiver = new Account();
        receiver.setNuggerPoint(100L);

        // Act
        transactionService.transferNuggerPointToAccount(sender, receiver, 10L);

        assertEquals(0L, sender.getNuggerPoint(), "Nugger point should not change.");
        assertEquals(100L, receiver.getNuggerPoint(), "Nugger point should not change.");
    }
}