package com.newrise.applicanttrackingsystem.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.repository.FeedbacksRepository;
import com.newrise.applicanttrackingsystem.services.IFeedbackServices;

@Service
public class FeedbackServiceImpl implements IFeedbackServices 
{
	@Autowired
	private FeedbacksRepository feedbacksRepository;
	
}
