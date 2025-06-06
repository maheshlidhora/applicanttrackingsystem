package com.newrise.applicanttrackingsystem.entities;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomUserDetails implements UserDetails 
{

	private static final long serialVersionUID = 1L;
	private Optional<Users> user;

	public CustomUserDetails(Optional<Users> user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return user.get().getPassword();
	}

	@Override
	public String getUsername() {
		return user.get().getEmail();
	}
}