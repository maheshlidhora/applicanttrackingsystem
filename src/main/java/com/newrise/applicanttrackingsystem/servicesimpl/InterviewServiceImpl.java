package com.newrise.applicanttrackingsystem.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.repository.InterviewsRepository;
import com.newrise.applicanttrackingsystem.services.IInterviewServices;

@Service
public class InterviewServiceImpl implements IInterviewServices 
{
	@Autowired
	private InterviewsRepository interviewsRepository;
	
}
