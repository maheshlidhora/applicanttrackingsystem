package com.newrise.applicanttrackingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Feedback;
import com.newrise.applicanttrackingsystem.entities.Interview;

public interface FeedbacksRepository extends JpaRepository<Feedback, Long>
{
	List<Feedback> findByInterview(Interview interview);
	List<Feedback> findByRating(int rating);
}
