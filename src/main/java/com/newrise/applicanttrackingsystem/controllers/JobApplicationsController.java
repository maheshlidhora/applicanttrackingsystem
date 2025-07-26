package com.newrise.applicanttrackingsystem.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.JobApplications;
import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.JobsRepository;
import com.newrise.applicanttrackingsystem.services.IApplicationService;
import com.newrise.applicanttrackingsystem.services.ITokenServices;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class JobApplicationsController 
{
	@Autowired
	private JobsRepository jobsRepository;
	@Autowired
	private ITokenServices iTokenServices;
	@Autowired
	private IApplicationService iApplicationService;
	
	@PostMapping("/applyForJob/{jobId}")
	@PreAuthorize("hasRole('Candidate')")
	public ResponseEntity<Map<String, Object>> applyForJob(@PathVariable long jobId) {
	    Map<String, Object> response = new HashMap<>();
	    Users candidate = iTokenServices.getCurrentUserObj();
	    Optional<Jobs> optionalJob = jobsRepository.findById(jobId);
	    if (optionalJob.isEmpty()) {
	        response.put("success", false);
	        response.put("message", "Job not found. Please provide a valid Job ID.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	    Jobs job = optionalJob.get();
	    if (!job.isActive()) {
	        response.put("success", false);
	        response.put("message", "Job is no longer active.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	    if (job.getClosingDate().isBefore(LocalDate.now())) {
	        response.put("success", false);
	        response.put("message", "This job posting has expired and is no longer accepting applications.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	    Optional<JobApplications> existingApplication = iApplicationService.findByCandidateAndApplicationId(candidate, job.getJobId());
	    if (existingApplication.isPresent()) {
	        response.put("success", false);
	        response.put("message", "You have already applied for this job.");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	    }
	    boolean applicationCreated = iApplicationService.applyForJob(job, candidate);
	    if (applicationCreated) {
	        response.put("success", true);
	        response.put("message", "Application submitted successfully.");
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } else {
	        response.put("success", false);
	        response.put("message", "Application submission failed due to an internal error.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	@GetMapping(value = {"/findAppliedApplications", "/findAppliedApplications/"})
	@PreAuthorize("isAuthenticated() and hasRole('Candidate')")
	public ResponseEntity<Page<JobApplications>>  findAllAppliedApplicationByCandidate(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
			){
		Users candidate = iTokenServices.getCurrentUserObj();
		Pageable pageable = PageRequest.of(page, size);
        Page<JobApplications> applicationsPage = iApplicationService.findAllAppliedApplications(candidate, pageable);
        if (applicationsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(applicationsPage);
        }
	}
}
