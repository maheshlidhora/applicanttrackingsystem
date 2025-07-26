package com.newrise.applicanttrackingsystem.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.newrise.applicanttrackingsystem.entities.JobApplications;
import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.entities.Users;

public interface IApplicationService 
{
	public boolean applyForJob(Jobs jobs, Users candidate);
	public Page<JobApplications> findAllAppliedApplications(Users candidate, Pageable pageable); // For List Out all Applied Application to Candidate.
	public Optional<JobApplications> findByCandidateAndApplicationId(Users candidate, Long applicationId);	// FindCandidateApplication --> To Check & Update Status
	public boolean updateApplication(Users candidate, Long applicationId);
}
