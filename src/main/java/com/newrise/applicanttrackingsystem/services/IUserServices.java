package com.newrise.applicanttrackingsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.newrise.applicanttrackingsystem.entities.Users;

public interface IUserServices 
{
	public String registerUserDetails(Users users);
	public List<Users> allUsers();
	public Optional<Users> findUserDetails(String email, String password);
	public Users findUser(String email);
	public String upadateUser(Users users);
	public String deleteUser(Users users);
	public String varifyUser(Users users);
	public String disableUser(String email);
	public String enableUser(String email);
	
	public void blacklistedAllTokens(Users user);	
	Page<Users> getAllUsersPaginated(Pageable pageable);
}
