package com.newrise.applicanttrackingsystem.services;

import java.util.Optional;

import com.newrise.applicanttrackingsystem.entities.Users;

public interface UserServices 
{
	public Optional<Users> findUserDetails(String email, String password);
	public String registerUserDetails(Users users);
}
