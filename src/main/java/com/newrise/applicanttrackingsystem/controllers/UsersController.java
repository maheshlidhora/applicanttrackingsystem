package com.newrise.applicanttrackingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.services.UserServices;


@RestController
@CrossOrigin("*")
//@RequestMapping("/User/Roles")
public class UsersController 
{
	@Autowired
	private UserServices userServices;

}
