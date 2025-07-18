package com.newrise.applicanttrackingsystem.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.services.IJobService;
import com.newrise.applicanttrackingsystem.services.ITokenServices;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class JobesController {
	@Autowired
	private IJobService iJobService;
	@Autowired
	private ITokenServices iTokenServices;
	@PostMapping("/createJob")
	@PreAuthorize("hasRole('HR Manager')")
	public ResponseEntity<Map<String, Object>> insertPostJob(@Valid @RequestBody Jobs job) 
	{
		Map<String, Object> response = new HashMap<>();
		boolean status = job.getTitle() != null && job.getDescription() != null && job.getDepartment() != null
				&& job.getLocation() != null && job.getExperience() != null && job.getSalary() != null
				&& job.getOpenings() != null && job.getCreatedBy() != null;
		if (status) {
			response.put("success", false);
			response.put("message",
					"Provide all the necessary details for Job-Creation such as: Title, Description, Department, Location, Experience, Salary & Openings.");
			return ResponseEntity.badRequest().body(response);
		}
		job.setCreatedBy(iTokenServices.getCurrentUserObj());
		boolean postStatus = iJobService.createPost(job);
		response.put("success", postStatus);
		if (postStatus) {

			response.put("message", "Post Created successfully.");
		} else {
			response.put("message", "Post is'nt created due to some error.");
		}

		return ResponseEntity.ok(response);
	}
}
