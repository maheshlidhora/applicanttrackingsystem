package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.newrise.applicanttrackingsystem.entities.Token;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.TokensRepository;
import com.newrise.applicanttrackingsystem.services.ITokenServices;
import com.newrise.applicanttrackingsystem.services.IUserServices;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements ITokenServices
{
	@Autowired 
	private JWTService jwtService;
	@Autowired
	private TokensRepository tokensRepository;
	@Autowired
	private IUserServices iUserServices;

	private String CURRENT_TOKEN;
	private String CURRENT_USER;
	private Users CURRENT_USER_OBJ;
	
	public HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			throw new IllegalStateException("No current request is available.");
		}
		return attributes.getRequest();
	}
	
	public void verifiyAndIntitialize() {
		HttpServletRequest request = getCurrentRequest();

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			this.CURRENT_TOKEN = getExtractedTokenByHeader(authHeader);
		}
		if (CURRENT_TOKEN != null && validateToken(CURRENT_TOKEN)) {
			this.CURRENT_USER = jwtService.extractUserName(CURRENT_TOKEN);
		}
		if (CURRENT_USER != null) {
			this.CURRENT_USER_OBJ = iUserServices.findUser(CURRENT_USER);
		}
	}
	
	@Override
	public String getExtractedTokenByHeader(String authHeader) {
		return authHeader.substring(7);
	}

	@Override
	public String getUserNameByExtractedTokenHeader(String token) {
		return jwtService.extractUserName(token);
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

	@Override
	public String getCurrentToken() {
		verifiyAndIntitialize();
		return CURRENT_TOKEN;
	}

	@Override
	public String getCurrentUser() {
		verifiyAndIntitialize();
		return CURRENT_USER;
	}

	@Override
	public Users getCurrentUserObj() {
		verifiyAndIntitialize();
		return CURRENT_USER_OBJ;
	}
}
