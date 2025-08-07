package com.newrise.applicanttrackingsystem.servicesimpl;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.InterviewsRepository;
import com.newrise.applicanttrackingsystem.services.IInterviewServices;

@Service
public class InterviewServiceImpl implements IInterviewServices 
{
	@Autowired
	private InterviewsRepository interviewsRepository;

	@Override
	public boolean createInterview(Interview interview) 
	{
		try 
		{
			interviewsRepository.save(interview);
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean isTimeSlotTaken(Users interviewer, LocalDate scheduledDate, LocalTime scheduledTime) {
	    return interviewsRepository.existsByInterviewerAndScheduledDateAndScheduledTime(interviewer, scheduledDate, scheduledTime);
	}


}
