package com.example.demo.service;
import com.example.demo.entity.User;
//import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public List<User> getUser(){
		return userrepo.findAll();
	}
	
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userrepo.save(user);
	}
}
