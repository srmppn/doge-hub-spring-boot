package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.sound.midi.Receiver;

import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.services.AccountService;
import com.example.demo.services.TransactionService;
import com.example.demo.services.exception.InsufficientNuggerPointException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class TestTransactionController {
    @Mock
    private TransactionController transactionController;
    private TransactionService transactionService;
    private AccountService accountService;
    private AccountRepository accountRepository;
    @Rule public MockitoRule mRUle = MockitoJUnit.rule();

    @Before
    public void setUp(){
        this.transactionController = new TransactionController();
    }

    @Test
    public void transferBalanceToAccount_ShouldTransferSucessfully() throws Exception{
        // Arrange
        Long amoutOfNugger = 10L;
        Account sender = new Account();
        sender.setNuggerPoint(100L);
        Account receiver = new Account();
        receiver.setNuggerPoint(100L);

        //when(accountRepository.save(Mockito.any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
        when(accountService.getAccountById(1L)).thenReturn(Optional.of(sender));
        when(accountService.getAccountById(2L)).thenReturn(Optional.of(receiver));
        // Act
        // accountRepository.save(sender);
        // accountRepository.save(receiver);
        // transactionController.sendNuggerPoint(1L, 2L, amoutOfNugger);

        // Assert
        verify(accountService, times(2)).getAccountById(anyLong());
    }
}