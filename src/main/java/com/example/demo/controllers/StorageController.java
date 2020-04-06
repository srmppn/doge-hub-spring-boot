package com.example.demo.controllers;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.example.demo.entities.FileResponse;
import com.example.demo.services.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
public class StorageController {
    @Autowired
    private StorageService storageService;
    
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = storageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                            .path("/downloadFile/")
                                                            .path(fileName)
                                                            .toUriString();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    // Bug for sure even did not test, but i knew it for some reason
    // @RequestMapping(value = "/file", method = RequestMethod.POST)
    // public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    //     return new ResponseEntity<>(Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList()), HttpStatus.CREATED);
    // }
}