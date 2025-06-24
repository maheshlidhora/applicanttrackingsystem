package com.newrise.applicanttrackingsystem.services;

import java.util.List;
import java.util.Optional;

import com.newrise.applicanttrackingsystem.entities.Users;

import io.jsonwebtoken.JwtBuilder;

public interface IUserServices 
{
	public String registerUserDetails(Users users);
	public List<Users> allUsers();
	public Optional<Users> findUserDetails(String email, String password);
	public Users findUser(String email);
//	public String upadateUser();
	public String deleteUser(Users users);
	public String varifyUser(Users users);
	
	public String disableUser(String email);
	public String enableUser(String email);
}
