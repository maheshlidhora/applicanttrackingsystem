package com.newrise.applicanttrackingsystem.services;

import java.util.List;
import java.util.Optional;

import com.newrise.applicanttrackingsystem.entities.Feedback;
import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.entities.JobApplications;

public interface IFeedbackServices 
{
//	Create / Submit Feedback (Rate Candidate)
	public boolean createFeedback(Feedback feedback);
	
//	Find Feedback by ID
	public Optional<Feedback> getFeedback(Long feedbackId);
	
//	Update Feedback
	public Feedback updateFeedback(Feedback feedback);
	
//	View Feedbacks by Interview ID
	public List<Feedback> getFeedbacksOnAnInterview(Interview interview);
	
//	Delete Feedback
	public boolean deleteFeedback(Feedback feedback);
	
//	List All Feedback
//	--> Based on An Application
	public List<Feedback> getAllFeedbacksOnAnApplication(JobApplications applications);
	
//	--> Based on Feedback Rating
	public List<Feedback> getAllFeedbacksByRatings(int rating);
	
}
