package com.example.demo.entities;

public class Transaction {
    private Long receiverId;
    private Long senderId;
    private Long amoutOfNugger;
    public Long getSenderId() {
        return senderId;
    }
    public Long getReceiverId() {
        return receiverId;
    }
    public Long getAmoutOfNugger() {
        return amoutOfNugger;
    }
}