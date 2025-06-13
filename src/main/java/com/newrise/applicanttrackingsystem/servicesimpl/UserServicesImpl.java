package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;
import com.newrise.applicanttrackingsystem.services.OtpService;
import com.newrise.applicanttrackingsystem.services.UserServices;

import io.jsonwebtoken.JwtBuilder;

@Service
public class UserServicesImpl implements UserServices 
{
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
//	@Autowired
//	private OtpService otpService;
	
	//	For Password Encryption
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
	
	@Override
	public Optional <Users> findUserDetails(String email, String password) 
	{
		Optional<Users> users = usersRepository.findByEmail(email);
		if (users.isPresent() && bCryptPasswordEncoder.matches(password, users.get().getPassword())) 
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
}
