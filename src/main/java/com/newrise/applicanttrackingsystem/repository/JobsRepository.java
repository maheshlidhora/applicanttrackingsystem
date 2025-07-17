package com.newrise.applicanttrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Jobs;

public interface JobsRepository extends JpaRepository<Jobs, Long>
{
	
}
