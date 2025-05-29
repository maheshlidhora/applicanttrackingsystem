package com.newrise.applicanttrackingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Users;


public interface UsersRepository extends JpaRepository<Users, Long> 
{
	Optional<Users> findByEmail(String email);
}
