package com.newrise.applicanttrackingsystem.services;

import java.time.LocalDate;
import java.time.LocalTime;

import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.entities.Users;

public interface IInterviewServices 
{
	public boolean createInterview(Interview interview); // 	--> 	scheduleInterview
	public Interview fetchinterview(long interviewId);
	public boolean isTimeSlotTaken(Users interviewer, LocalDate scheduledDate, LocalTime scheduledTime);
	public String updateInterviewStatus(long applicationId, String applicationStatus);
	
	//	Get All Interviews – list all interviews (with pagination & filtering).	--> Using Switch cases for Filtering on different applicationStatus
	//	Send Notifications by Email	
	public Interview updateInterview(Interview interview);
}
