package com.example.demo.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationProvider {
    @Autowired
    AccountDetailsService accountDetailsService;
    public Authentication authenticate(Authentication authentication) throws UsernameNotFoundException{
        UsernamePasswordAuthenticationToken authenticationToken = null;
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails account = accountDetailsService.loadUserByUsername(username);
        if(account != null){
            if(username.equals(account.getUsername()) && password.equals(account.getPassword())){
                authenticationToken = new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(account));
            }
            else{
                throw new UsernameNotFoundException("Username not found.");
            }
        }
        else {
            throw new UsernameNotFoundException(username);
        }
        return authenticationToken;
    }
    private Collection<GrantedAuthority> getGrantedAuthorities(UserDetails account) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(account.getAuthorities().equals("admin")){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return grantedAuthorities;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}