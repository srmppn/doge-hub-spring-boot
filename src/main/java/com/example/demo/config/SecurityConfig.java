package com.example.demo.config;

import com.example.demo.entities.Account;
import com.example.demo.filters.JwtRequestFilter;
import com.example.demo.services.AccountDetailsService;
import com.example.demo.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	private AccountDetailsService accountDetailsService;
	@Autowired
	private AccountService accountService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		this.accountService.saveAccount(new Account("pink", passwordEncoder().encode("1234"), 10L));
		this.accountService.saveAccount(new Account("guy", passwordEncoder().encode("1234"), 10L));
		auth.userDetailsService(accountDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors();
		httpSecurity.csrf().disable()
				.authorizeRequests()
						.antMatchers("/api/v1/signin").permitAll()
						.antMatchers("/api/v1/file").permitAll()
						.antMatchers("/api/v1/signup").permitAll()
						.anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
}