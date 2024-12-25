package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.JWTAuthenticationFilter;
import com.example.demo.security.JwtAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {
	
	 @Autowired
	    private JwtAuthenticationEntryPoint point;
	   
	 @Autowired
	    private JWTAuthenticationFilter filter;
	 
	 @Autowired
	 private UserDetailsService userDetailService;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 
		 http.csrf(csrf ->csrf.disable())
		     .cors(cors->cors.disable())
		     .authorizeHttpRequests(auth->auth.requestMatchers("/home/**")
		    		 .authenticated()
		    		 .requestMatchers("/auth/login")
		    		 .permitAll().requestMatchers("/auth/create-user").permitAll().anyRequest().authenticated())
		     
		     .exceptionHandling(ex ->ex.authenticationEntryPoint(point))
		     .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		 
		 http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
		 return http.build();
	 }
	 
	 @Bean
	 public DaoAuthenticationProvider daoAuthenticationProvider() {
		 DaoAuthenticationProvider daoAuthenticationProvider = new  DaoAuthenticationProvider();
		 daoAuthenticationProvider.setUserDetailsService(userDetailService);
		 daoAuthenticationProvider.setPasswordEncoder( passwordEncoder);
		 return  daoAuthenticationProvider;
	 }

}