package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;
import com.newrise.applicanttrackingsystem.services.IOtpService;
import com.newrise.applicanttrackingsystem.services.IUserServices;

@Service
public class UserServicesImpl implements IUserServices 
{
	@Autowired
	private UsersRepository usersRepository;
	
<<<<<<< HEAD
=======
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
//	@Autowired
//	private OtpService otpService;
	
	//	For Password Encryption
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
	
>>>>>>> d7282b574a48ffe8b4885e02b06454390eb87a14
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
<<<<<<< HEAD
	
=======

	@Override
	public String registerUserDetails(Users users) 
	{
		if (users != null) 
		{
			users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
			try 
			{
				usersRepository.save(users);
			} 
			catch (Exception e) 
			{
				return "This contact is already registered. Please choose another contact.";
			}
			return "User Registered Successfully!!";
		} 
		else 
		{
			return "User Not Registered!!";
		}
	}

	@Override
	public String varifyUser(Users users) 
	{
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(users.getEmail(), users.getPassword()));
		if (authentication.isAuthenticated()) 
		{
			return jwtService.generateToken(users);
		}
		return null;
	}
>>>>>>> d7282b574a48ffe8b4885e02b06454390eb87a14
}
