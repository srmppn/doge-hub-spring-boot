package com.example.demo.controllers;

import java.util.Base64;

import javax.validation.Valid;

import com.example.demo.entities.Account;
import com.example.demo.services.AccountDetailsService;
import com.example.demo.services.SignUpAccountService;
import com.example.demo.services.exception.UsernameInflictException;
import com.example.demo.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class AuthenticationController {
    @Autowired
    private AccountDetailsService accountDetailsService;
    @Autowired
    private SignUpAccountService signUpAccountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
	private JwtUtil jwtTokenUtil;
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> getAuthenticate(@RequestHeader("Authorization") String authorization) throws Exception{
        String encodedCredentials = authorization.substring(6);
        byte[] decodedCredentialByte = Base64.getDecoder().decode(encodedCredentials);
        String[] decodedCredentialString = new String(decodedCredentialByte).split(":");
        try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(decodedCredentialString[0], decodedCredentialString[1])
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = this.accountDetailsService.loadUserByUsername(decodedCredentialString[0]);
		final String jwt = "Bearer " + jwtTokenUtil.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);
		return new ResponseEntity<>(headers ,HttpStatus.OK);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> SignUpNewAccount(@RequestBody @Valid Account account){
        try{
            this.signUpAccountService.SignUpAccount(account);
        }
        catch(UsernameInflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}