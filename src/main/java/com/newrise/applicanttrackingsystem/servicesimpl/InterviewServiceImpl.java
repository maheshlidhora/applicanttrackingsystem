package com.newrise.applicanttrackingsystem.servicesimpl;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.ApplicationStatus;
import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.entities.JobApplications;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.ApplicationStatusRepository;
import com.newrise.applicanttrackingsystem.repository.ApplicationsRepository;
import com.newrise.applicanttrackingsystem.repository.InterviewsRepository;
import com.newrise.applicanttrackingsystem.services.IInterviewServices;

@Service
public class InterviewServiceImpl implements IInterviewServices {
	@Autowired
	private InterviewsRepository interviewsRepository;
	@Autowired
	private ApplicationsRepository applicationsRepository;
	@Autowired
	private ApplicationStatusRepository statusRepository;

	@Override
	public boolean createInterview(Interview interview) {
		try {
			interviewsRepository.save(interview);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isTimeSlotTaken(Users interviewer, LocalDate scheduledDate, LocalTime scheduledTime) {
		return interviewsRepository.existsByInterviewerAndScheduledDateAndScheduledTime(interviewer, scheduledDate,
				scheduledTime);
	}

	@Override
	public String updateInterviewStatus(long applicationId, String applicationStatus) {
		JobApplications applications = null;
		ApplicationStatus status = null;
		try {
			applications = applicationsRepository.findById(applicationId).orElseGet(() -> null);
			try {
				status = statusRepository.findByStatusName(applicationStatus).orElseGet(null);
			} catch (Exception e) {
				e.printStackTrace();
				return "Role doesn't Exist..!!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Application doesn't Exist..!!";
		}
		if (applications != null && status != null) {
			applications.setApplicationStatus(status);
		}
		return "Status is set on " + status.getStatusName() + ".";
	}

	@Override
	public Interview fetchinterview(Long interviewId) {
		try {
			return interviewsRepository.findById(interviewId).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Interview updateInterview(Interview interview) 
	{
		try {
			return interviewsRepository.save(interview);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
