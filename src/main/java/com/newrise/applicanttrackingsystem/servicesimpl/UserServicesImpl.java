package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Roles;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;
import com.newrise.applicanttrackingsystem.services.IOtpService;
import com.newrise.applicanttrackingsystem.services.IUserServices;

import io.jsonwebtoken.JwtBuilder;

@Service
public class UserServicesImpl implements IUserServices 
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
			users.setUserType(users.getRoles().stream().map(Roles::getRoleName).collect(Collectors.joining(" + ")));
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

	@Override
	public List<Users> allUsers() 
	{
		return usersRepository.findAll();
	}

	@Override
	public String deleteUser(Users users) 
	{
		Users tagetUser = null;
		try 
		{
			tagetUser = usersRepository.findByEmail(users.getEmail()).get();
		} 
		catch (Exception e) 
		{
			return "User Not Found..!!";
		}
		if (tagetUser.getEmail().equals(users.getEmail())) 
		{
			usersRepository.deleteById(tagetUser.getUserId());
			return "User Deleted Successfully!!"; 
		}
		return "User Not Deleted due to some error..!!";
	}

	@Override
	public Users findUser(String email) 
	{
		try 
		{
			Users user = usersRepository.findByEmail(email).get();
			return (user!=null)?user:null;
		} 
		catch (Exception e) 
		{
			return null;
		}
	}

	@Override
	public String disableUser(String email) 
	{
		try 
		{
			Users user = usersRepository.findByEmail(email).get();
			user.setBlocked(true);
			usersRepository.save(user);
			return "User is disabled for Login..!!";
		} 
		catch (Exception e) 
		{
			return "User Not Found..!!";
		}
	}

	@Override
	public String enableUser(String email) 
	{
		try 
		{
			Users user = usersRepository.findByEmail(email).get();
			user.setBlocked(false);
			usersRepository.save(user);
			return "User is enabled for Login..!!";
		} 
		catch (Exception e) 
		{
			return "User Not Found..!!";
		}
	}
}
