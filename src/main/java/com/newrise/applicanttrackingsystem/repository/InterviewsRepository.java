package com.newrise.applicanttrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Interview;

public interface InterviewsRepository extends JpaRepository<Interview, Long>
{
	
}
