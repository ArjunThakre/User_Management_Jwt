package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {

	
//    @Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails userdetail = User.builder().username("Ankit")
//				                 .password(passwordEncoder().encode("Ankit")).roles("ADMIN").build();
//		UserDetails userdetail1 = User.builder().username("Arjun")
//                .password(passwordEncoder().encode("ARJUN")).roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(userdetail,userdetail1);
//	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
