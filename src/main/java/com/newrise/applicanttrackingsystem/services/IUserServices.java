package com.newrise.applicanttrackingsystem.services;

import java.util.Optional;

import com.newrise.applicanttrackingsystem.entities.Users;

import io.jsonwebtoken.JwtBuilder;

public interface IUserServices 
{
	public Optional<Users> findUserDetails(String email, String password);
	public String registerUserDetails(Users users);
	public String varifyUser(Users users);
}
