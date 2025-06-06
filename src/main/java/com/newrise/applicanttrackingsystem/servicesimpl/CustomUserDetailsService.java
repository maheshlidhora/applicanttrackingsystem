package com.newrise.applicanttrackingsystem.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.CustomUserDetails;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UsersRepository usersRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return new CustomUserDetails(usersRepository.findByEmail(email));
	}
}
