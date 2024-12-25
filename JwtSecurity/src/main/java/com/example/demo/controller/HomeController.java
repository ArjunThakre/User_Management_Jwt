package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private UserService userservice;
	
	@GetMapping("/user")
	public List<User> getUser() {
		System.out.println("Getting Users");
		return userservice.getUser();
	}
	
	@GetMapping("/current")
	public String getLoggedUser(Principal principal) {
		return principal.getName();
	}

}
