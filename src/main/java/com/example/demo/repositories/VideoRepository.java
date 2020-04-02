package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByTitle(String title);
}