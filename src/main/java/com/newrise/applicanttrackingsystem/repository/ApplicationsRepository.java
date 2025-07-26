package com.newrise.applicanttrackingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.JobApplications;
import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.entities.Users;


public interface ApplicationsRepository extends JpaRepository<JobApplications, Long>
{
//	--------------------------		FOR CANDIDATE		--------------------------
//	Find All by Candidate ID --> List of Optional Objects
//	Find by Candidate ID & Application ID --> Single Optional Object
//	Update by Candidate ID & Application ID (Not Required)
	
	Page<JobApplications> findByCandidate(Users candidate, Pageable pageable);
	Optional<JobApplications> findByCandidateAndApplicationId(Users candidate, Long applicationId);

//	--------------------------		FOR HR		--------------------------
//	Find by JOB ID	--> List of Optional Objects
//	Find Application by JOB ID & Application ID --> Single Optional Object (To interact with Candidate)
//	Update in Application Status By Application by JOB ID (Not Required)
	
	List<JobApplications> findByJob(Jobs job);
	Optional<JobApplications> findByJobAndApplicationId(Jobs job, Long applicationId);
}
