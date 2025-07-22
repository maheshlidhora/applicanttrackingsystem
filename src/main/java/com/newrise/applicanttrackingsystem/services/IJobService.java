package com.newrise.applicanttrackingsystem.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.entities.Users;

public interface IJobService 
{
//	------------------------- FOR HR -------------------------
	public boolean createJobs(Jobs job);
	Optional<Jobs> findByCreatedByAndHrId(Users createdBy, long jobId);
	public Page<Jobs> getAllJobsToHR(Users createdBy, Pageable pageable);
	public boolean updateJob(Users createdBy, Jobs job, long jobId);
	public boolean deleteJobByHR(Users createdBy, long jobId);
	
//	------------------------- FOR CANDIDATE -------------------------
	Page<Jobs> getAllJobsPaginated(Pageable pageable);

	
	
}
