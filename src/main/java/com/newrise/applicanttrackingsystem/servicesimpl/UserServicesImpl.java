package com.newrise.applicanttrackingsystem.servicesimpl;

import java.time.ZoneId;
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
import com.newrise.applicanttrackingsystem.entities.Token;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.TokensRepository;
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
	private TokensRepository tokensRepository;
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
			// Get user from DB
	        Users dbUser = usersRepository.findByEmail(users.getEmail()).orElse(null);
	        if (dbUser == null) return null;
	        // Blacklist all old valid tokens
	        blacklistedAllTokens(dbUser);
	        // Now Generate New Token
	        String generatedToken = jwtService.generateToken(users);
	        
	        Token token = new Token();
	        token.setToken(generatedToken);
	        token.setBlacklisted(false);
	        token.setExpired(false);
	        token.setCreatedAt(java.time.LocalDateTime.now());
	        token.setExpiresAt(jwtService.extractExpiration(generatedToken)
	        		.toInstant()
	        	    .atZone(ZoneId.systemDefault())
	        	    .toLocalDateTime());
	        token.setUserId(dbUser);
	        tokensRepository.save(token);
	        
			return generatedToken;
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

	@Override
	public String upadateUser(Users users) 
	{
		try 
		{
			Users user = usersRepository.findByEmail(users.getEmail()).get();
			usersRepository.save(user);
			return "User updated successfully..!!";
		} 
		catch (Exception e) 
		{
			return "User Not Found..!!";
		}
	}

	@Override
	public void blacklistedAllTokens(Users user) 
	{
	    List<Token> validTokens = tokensRepository.findAllByUserAndExpiredIsFalseAndIsBlacklistedFalse(user);
	    if (validTokens.isEmpty()) return;

	    for (Token token : validTokens) {
	        token.setExpired(true);
	        token.setBlacklisted(true);
	    }
	    tokensRepository.saveAll(validTokens);
	}

	@Override
	public boolean validateToken(String token) 
	{
	    try 
	    {
	        // Validate JWT Structure & Signature
	        String username = jwtService.extractUserName(token);
	        if (username == null) return false;
	        // Check into Database for token validity
	        Optional<Token> tokenOptional = tokensRepository.findByToken(token);
	        if (tokenOptional.isEmpty()) return false;
	        Token storedToken = tokenOptional.get();
	        return !storedToken.isExpired() && !storedToken.isBlacklisted();
	    } 
	    catch (Exception e) 
	    {
	        return false;
	    }
	}
}
