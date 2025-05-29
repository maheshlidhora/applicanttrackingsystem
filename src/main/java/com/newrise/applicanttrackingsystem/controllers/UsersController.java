package com.newrise.applicanttrackingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.services.UserServices;


@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UsersController 
{
	@Autowired
	private UserServices userServices;
	
	@GetMapping("/")
	public String getUserIndex()
	{
		return "User-Index";
	}
	
	@PostMapping("/login")
	public Users getUserLogedin(@RequestBody Users users) 
	{
		return userServices.findUserDetails(users.getEmail(), users.getPassword()).orElseThrow(() -> new RuntimeException("User not found"));
	}
}
