package com.example.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long accountId;
    @NotBlank(message = "The username is mandatory")
    private String userName;
    @NotBlank(message = "Need to specify password")
    private String passWord;
    private Long nuggerPoint;
    @OneToMany(mappedBy = "account")
    private List<Video> videos;
    
    public Account(){

    }
    public Account(String username, String password, Long nuggerpoint){
        this.userName = username;
        this.passWord = password;
        this.nuggerPoint = nuggerPoint;
    }
    public Long getAccountId(){
        return accountId;
    }
    public void setAccountId(Long accountId){
        this.accountId = accountId;
    }
    public String getUserName(){
        return this.userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    @JsonIgnore
    public String getPassword(){
        return this.passWord;
    }
    @JsonDeserialize
    public void setPassword(String password){
        this.passWord = password;
    }
    public Long getNuggerPoint(){
        return this.nuggerPoint;
    }
    public void setNuggerPoint(Long nuggerPoint){
        this.nuggerPoint = nuggerPoint;
    }
    public List<Video> getVideos() {
        return videos;
    }
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}