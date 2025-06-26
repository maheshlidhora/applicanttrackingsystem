package com.newrise.applicanttrackingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Token;
import com.newrise.applicanttrackingsystem.entities.Users;


public interface TokensRepository extends JpaRepository<Token, Long>
{
	Optional<Token> findByToken(String token);
	List<Token> findAllByUserAndExpiredIsFalseAndIsBlacklistedFalse(Users user);
	List<Token> findAllByUserAndExpiredIsTrueAndIsBlacklistedTrue(Users user);
}
