package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "video")
public class Video {

    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID", updatable = false, nullable = false)
    private Long videoId;
    private String title;
    private String duration;
    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    public Long getVideoId(){
        return this.videoId;
    }
    public void setVideoId(Long id){
        this.videoId = id;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDuration(){
        return this.duration;
    }
    public void setDuration(String duration){
        this.duration = duration;
    }
    public void setAccount(Account account){
        this.account = account;
    }
}