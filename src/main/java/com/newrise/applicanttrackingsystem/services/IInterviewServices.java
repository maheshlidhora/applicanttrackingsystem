package com.newrise.applicanttrackingsystem.services;

import java.time.LocalDate;
import java.time.LocalTime;

import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.entities.Users;

public interface IInterviewServices 
{
	public boolean createInterview(Interview interview); // 	--> 	scheduleInterview
	boolean isTimeSlotTaken(Users interviewer, LocalDate scheduledDate, LocalTime scheduledTime);
}
