package com.newrise.applicanttrackingsystem.services;

import com.newrise.applicanttrackingsystem.entities.Users;

public interface ITokenServices 
{
	public String getExtractedTokenByHeader(String authHeader);
	public String getUserNameByExtractedTokenHeader(String token);
	public boolean validateToken(String token);
	public String getCurrentToken();
	public String getCurrentUser();
	public Users getCurrentUserObj();
}
