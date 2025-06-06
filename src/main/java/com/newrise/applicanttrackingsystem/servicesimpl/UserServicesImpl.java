package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;
import com.newrise.applicanttrackingsystem.services.UserServices;

@Service
public class UserServicesImpl implements UserServices 
{
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public Optional <Users> findUserDetails(String email, String password) 
	{
		Optional<Users> users = usersRepository.findByEmail(email);
		if (users.isPresent() && users.get().getPassword().equals(password)) 
		{
			return users;
		}
		return Optional.empty();
	}

	@Override
	public String registerUserDetails(Users users) 
	{
		if (users != null) 
		{
			usersRepository.save(users);
			return "User Registered Successfully!!";
		} 
		else 
		{
			return "User Not Registered!!";
		}
	}
	
}
