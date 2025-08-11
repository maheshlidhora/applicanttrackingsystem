package com.newrise.applicanttrackingsystem.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.entities.JobApplications;
import com.newrise.applicanttrackingsystem.entities.Mode;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.ApplicationsRepository;
import com.newrise.applicanttrackingsystem.repository.ModesRepository;
import com.newrise.applicanttrackingsystem.services.IInterviewServices;
import com.newrise.applicanttrackingsystem.services.ITokenServices;
import com.newrise.applicanttrackingsystem.services.IUserServices;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/interview")
public class InterviewController {
	@Autowired
	private ITokenServices tokenServices;
	@Autowired
	private ApplicationsRepository applicationsRepository;
	@Autowired
	private IUserServices userServices;
	@Autowired
	private IInterviewServices interviewServices;
	@Autowired
	private ModesRepository modesRepository;

	@PostMapping("/scheduleInterview")
	@PreAuthorize("isAuthenticated() and hasRole('HR Manager')")
	public ResponseEntity<Map<String, Object>> scheduleInterview(@Valid @RequestBody Interview interview) {
		Map<String, Object> response = new HashMap<>();
		try {
			// Get current authenticated HR user
			Users currentHr = tokenServices.getCurrentUserObj();
			if (currentHr == null) {
				response.put("error", "Unauthorized access.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			// Validate Job Application existence
			Optional<JobApplications> optionalApplication = applicationsRepository
					.findById(interview.getJobApplication().getApplicationId());
			if (optionalApplication.isEmpty()) {
				response.put("error", "Job application not found.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
			JobApplications jobApplication = optionalApplication.get();
			// Ensure current HR owns the job
			if (jobApplication.getJob().getCreatedBy().getUserId() != currentHr.getUserId()) {
				response.put("error", "You are not authorized to schedule interviews for this job.");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
			}
			// Validate Interviewer existence
			Users interviewer = userServices.findUser(interview.getInterviewer().getEmail());
			if (interviewer == null) {
				response.put("error", "Interviewer not found.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
			// Check if the user has "Interviewer" role
			boolean hasInterviewerRole = interviewer.getRoles().stream()
					.anyMatch(role -> "Interviewer".equalsIgnoreCase(role.getRoleName()));
			if (!hasInterviewerRole) {
				response.put("error", "The selected user is not assigned the 'Interviewer' role.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			// Validate Mode
			Optional<Mode> optionalMode = modesRepository.findByModeName(interview.getMode().getModeName());
			if (optionalMode.isEmpty()) {
				response.put("error", "Interview mode not found.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
			// Check for time-slot conflict (requires your implementation)
			boolean conflict = interviewServices.isTimeSlotTaken(interviewer, interview.getScheduledDate(),
					interview.getScheduledTime());
			if (conflict) {
				response.put("error", "The selected time slot is already booked for this interviewer.");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
			// Finalize and save interview
			interview.setJobApplication(jobApplication);
			interview.setInterviewer(interviewer);
			interview.setMode(optionalMode.get());
			boolean status = interviewServices.createInterview(interview);
			if (status) {
				response.put("message", "Interview scheduled successfully.");
				response.put("interview", interview);
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				response.put("error", "Failed to schedule interview. Please try again.");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", "An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/updateApplicationStatus")
	@PreAuthorize("isAuthenticated() and (hasRole('HR Manager') or hasRole('Interviewer'))")
	public ResponseEntity<Map<String, Object>> changeApplicationStatus(@RequestBody Map<String, Object> payload) {
		Map<String, Object> response = new HashMap<>();
		if (payload.get("applicationId") == null || payload.get("applicationStatus") == null) {
			response.put("error", "applicationId and applicationStatus are required.");
			return ResponseEntity.badRequest().body(response);
		}
		String applicationStatus = payload.get("applicationStatus").toString().trim();
		if (applicationStatus.isEmpty()) {
			response.put("error", "applicationStatus cannot be empty.");
			return ResponseEntity.badRequest().body(response);
		}
		long applicationId;
		try {
			applicationId = Long.parseLong(payload.get("applicationId").toString());
		} catch (NumberFormatException e) {
			response.put("error", "applicationId must be a valid number.");
			return ResponseEntity.badRequest().body(response);
		}
		String result = interviewServices.updateInterviewStatus(applicationId, applicationStatus);
		response.put("message", result);
		response.put("applicationId", applicationId);
		response.put("applicationStatus", applicationStatus);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getInterviewInfo/{interviewId}")
	@PreAuthorize("isAuthenticated() and (hasRole('HR Manager') or hasRole('Interviewer'))")
	public ResponseEntity<Map<String, Object>> getInterviewDetails(@PathVariable Long interviewId) {
		Map<String, Object> response = new HashMap<>();
		if (interviewId == null) {
			response.put("error", "Application ID cannot be null");
			return ResponseEntity.badRequest().body(response);
		}
		if (interviewId <= 0) {
			response.put("error", "Application ID must be greater than zero");
			return ResponseEntity.badRequest().body(response);
		}
		Interview interview = interviewServices.fetchinterview(interviewId);
		if (interview != null) {
			response.put("message", "Interview details fetched successfully");
			response.put("interview", interview);
			return ResponseEntity.ok(response);
		} else {
			response.put("error", "No interview found for the given Application ID");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@PatchMapping("/rescheduleInterview/{interviewId}")
	@PreAuthorize("isAuthenticated() and (hasRole('HR Manager') or hasRole('Interviewer'))")
	public ResponseEntity<Map<String, Object>> rescheduleInterview(
			@PathVariable Long interviewId,
			@RequestBody(required = false) Interview interview) 
	{
		Map<String, Object> response = new HashMap<>();
		// Validate Application ID
		if (interviewId == null || interviewId <= 0) {
			response.put("status", "error");
			response.put("message", "Invalid or missing interviewId");
			return ResponseEntity.badRequest().body(response);
		}
		// Validate Interview object
		if (interview == null) {
			response.put("status", "error");
			response.put("message", "Interview details are required");
			return ResponseEntity.badRequest().body(response);
		}
		// Validate Scheduled Date
		if (interview.getScheduledDate() == null) {
			response.put("status", "error");
			response.put("message", "Scheduled date and time must be provided");
			return ResponseEntity.badRequest().body(response);
		}
		// Fetch existing interview
		Interview oldInterview = interviewServices.fetchinterview(interviewId);
		if (oldInterview == null) {
			response.put("error", "No interview found for the given Application ID");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		// Validate Interviewer existence
		Users interviewer = userServices.findUser(interview.getInterviewer().getEmail());
		if (interviewer == null) {
			response.put("error", "Interviewer not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		// Ensure user has 'Interviewer' role
		boolean hasInterviewerRole = interviewer.getRoles().stream()
				.anyMatch(role -> "Interviewer".equalsIgnoreCase(role.getRoleName()));
		if (!hasInterviewerRole) {
			response.put("error", "The selected user is not assigned the 'Interviewer' role.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		// Check for time-slot conflict
		boolean conflict = interviewServices.isTimeSlotTaken(interviewer, interview.getScheduledDate(),
				interview.getScheduledTime());
		if (conflict) {
			response.put("error", "The selected time slot is already booked for this interviewer.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
		// Set interviewer
		oldInterview.setInterviewer(interviewer);
		// Validate and set Mode
		Optional<Mode> optionalMode = modesRepository.findByModeName(interview.getMode().getModeName());
		if (optionalMode.isEmpty()) {
			response.put("error", "Interview mode not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		oldInterview.setMode(optionalMode.get());
		// Update date & time
		oldInterview.setScheduledDate(interview.getScheduledDate());
		oldInterview.setScheduledTime(interview.getScheduledTime());
		// Save updated interview
		Interview updatedInterview = interviewServices.updateInterview(oldInterview);
		if (updatedInterview != null) {
			response.put("status", "success");
			response.put("message", "Interview rescheduled successfully");
			response.put("interview", updatedInterview);
			return ResponseEntity.ok(response);
		} else {
			response.put("error", "Interview not rescheduled due to an internal server error.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
