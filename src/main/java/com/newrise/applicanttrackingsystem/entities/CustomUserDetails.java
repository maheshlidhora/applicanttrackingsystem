package com.newrise.applicanttrackingsystem.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.newrise.applicanttrackingsystem.services.IUserServices;

import jakarta.servlet.http.HttpServletRequest;


public class CustomUserDetails implements UserDetails 
{	
	private static final long serialVersionUID = 1L;
	private Users user;
	
	@Autowired
	private HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	
	@Autowired
	private IUserServices iUserServices;

	public CustomUserDetails(Optional<Users> user) {
		super();
		this.user = user.get();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		Collection<GrantedAuthority> authorities = new ArrayList<>();
	    for (Roles role : user.getRoles()) 
	    {
	        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
	    }
	    return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
//		Checks whether the user's entire account is still valid or has expired.
//		Use case: If an account is set to expire after a certain period (e.g., a trial account), returning false will prevent login.
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
//		Purpose: Indicates whether the account is locked due to suspicious activity or policy.
	    return user.isVerified() && !user.isBlocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
//		Checks whether the user's credentials (usually the password) have expired.
//		Use case: If you enforce password expiry policies, this controls whether users must reset their password before logging in.
		return true;
	}

	@Override
	public boolean isEnabled() {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null) 
		{
			return this.isAccountNonLocked();
		}
        return (authHeader != null && authHeader.startsWith("Bearer "))?iUserServices.validateToken(authHeader.substring(7)):false;
	}
}