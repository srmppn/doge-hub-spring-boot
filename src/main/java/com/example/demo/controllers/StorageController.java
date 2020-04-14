package com.example.demo.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.entities.Account;
import com.example.demo.entities.FileResponse;
import com.example.demo.services.AccountService;
import com.example.demo.services.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private AccountService accountService;
    
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(Authentication authentication, @RequestParam("file") MultipartFile file) {
        Optional<Account> targetAccount = accountService.getAccountByName(authentication.getName());
        if(targetAccount.isPresent()){
            String fileName = storageService.storeFile(file);
            // String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            //                                                     .path("/images/")
            //                                                     .path(fileName)
            //                                                     .toUriString();
            targetAccount.get().setProfileImage(fileName);
            accountService.saveAccount(targetAccount.get());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/images/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = storageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new IOException("SASD");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    // Bug for sure even did not test, but i knew it for some reason
    // @RequestMapping(value = "/file", method = RequestMethod.POST)
    // public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    //     return new ResponseEntity<>(Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList()), HttpStatus.CREATED);
    // }
}