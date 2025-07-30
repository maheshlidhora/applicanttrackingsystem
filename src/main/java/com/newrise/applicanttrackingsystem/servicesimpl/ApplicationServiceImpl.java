package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.JobApplications;
import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.ApplicationStatusRepository;
import com.newrise.applicanttrackingsystem.repository.ApplicationsRepository;
import com.newrise.applicanttrackingsystem.services.IApplicationService;

@Service
public class ApplicationServiceImpl implements IApplicationService {
	@Autowired
	private ApplicationsRepository applicationsRepository;
	@Autowired
	private ApplicationStatusRepository statusRepository;

	@Override
	public boolean applyForJob(Jobs jobs, Users candidate) {
		JobApplications newApplications = new JobApplications();
		newApplications.setJob(jobs);
		newApplications.setCandidate(candidate);
		newApplications.setApplicationStatus(statusRepository.findByStatusName("Applied").get());
		try {
			applicationsRepository.save(newApplications);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Optional<JobApplications> findByCandidateAndApplicationId(Users candidate, Long applicationId) {
		try {
			return applicationsRepository.findByCandidateAndApplicationId(candidate, applicationId);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public Page<JobApplications> findAllAppliedApplications(Users candidate, Pageable pageable) {
		try {
			return applicationsRepository.findByCandidate(candidate, pageable);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean withdrawApplication(Users candidate, JobApplications application) {
		if (application!=null) {
			application.setApplicationStatus(statusRepository.findByStatusName("Withdraw").get());
			applicationsRepository.save(application);
			return true;
		}
		return false;
	}

}
