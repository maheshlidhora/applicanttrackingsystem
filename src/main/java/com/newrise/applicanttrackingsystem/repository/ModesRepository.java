package com.newrise.applicanttrackingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Mode;

public interface ModesRepository extends JpaRepository<Mode, Long> 
{
	Optional<Mode> findByModeName(String modeName);
}
