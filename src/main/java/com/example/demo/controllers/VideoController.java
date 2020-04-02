package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.demo.services.VideoService;
import com.example.demo.entities.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class VideoController {
    @Autowired
    VideoService service;
    
    @RequestMapping(value = "/videos", method = RequestMethod.GET)
    public ResponseEntity<?> getAllVideos(){
        return new ResponseEntity<>(service.getAllVideos(), HttpStatus.OK);
    }

    @RequestMapping(value = "/videos/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getVideoById(@PathVariable Long id){
        Optional<Video> v = service.getVideoById(id);
        if(v.equals(Optional.empty())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Video>(v.get(),HttpStatus.OK);
    }

    @RequestMapping(value = "/videos", method = RequestMethod.POST)
    public ResponseEntity<?> getVideoById(@Valid @RequestBody Video v){
        service.createVideo(v);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}