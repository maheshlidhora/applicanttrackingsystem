package com.newrise.applicanttrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Feedback;

public interface FeedbacksRepository extends JpaRepository<Feedback, Long>
{
	
}
