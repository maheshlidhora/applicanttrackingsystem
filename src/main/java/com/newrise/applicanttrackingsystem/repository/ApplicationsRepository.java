package com.newrise.applicanttrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.JobApplications;

public interface ApplicationsRepository extends JpaRepository<JobApplications, Long>
{
	
}
