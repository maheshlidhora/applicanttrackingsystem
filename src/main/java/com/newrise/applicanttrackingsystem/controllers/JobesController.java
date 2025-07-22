package com.newrise.applicanttrackingsystem.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.entities.Users;
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
		boolean postStatus = iJobService.createJobs(job);
		response.put("success", postStatus);
		if (postStatus) {

			response.put("message", "Post Created successfully.");
		} else {
			response.put("message", "Post is'nt created due to some error.");
		}

		return ResponseEntity.ok(response);
	}
	
	//    GET /api/users/allUsers?page=1&size=5
	//    Authorization: Bearer <token>
    @GetMapping(value = {"/getallJobs", "/getallJobs/"})
    @PreAuthorize("isAuthenticated() and hasRole('Candidate')")
    public ResponseEntity<Page<Jobs>> getAllJobsDetailsPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) 
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Jobs> jobsPage = iJobService.getAllJobsPaginated(pageable);
        return ResponseEntity.ok(jobsPage);
    }
    
    @PostMapping(value = {"/getallJobsToHR", "/getallJobsToHR/"})
    @PreAuthorize("isAuthenticated() and hasRole('HR Manager')")
    public ResponseEntity<Page<Jobs>> getAllJobsDetailsPaginatedToHR(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) 
    {
        Pageable pageable = PageRequest.of(page, size);
        Users users = iTokenServices.getCurrentUserObj();
        Page<Jobs> jobsPage = iJobService.getAllJobsToHR(users, pageable);
        return ResponseEntity.ok(jobsPage);
    }
    
    @DeleteMapping("/deleteJob/{jobId}")
    @PreAuthorize("isAuthenticated() and hasRole('HR Manager')")
    public ResponseEntity<Map<String, Object>> deleteJobDetailsByHR(@PathVariable long jobId) 
    {
    	Users users = iTokenServices.getCurrentUserObj();
    	boolean deleteStatus = iJobService.deleteJobByHR(users, jobId);
    	Map<String, Object> response = new HashMap<>();
    	if (deleteStatus) 
    	{
    		response.put("success", deleteStatus);
			response.put("message","Post Deleted Successfully.");
		}
    	else 
    	{
    		response.put("success", deleteStatus);
			response.put("message","Post is'nt Present.");
    	}
		return ResponseEntity.ok(response);
    }
    
    @PostMapping("/getJobDetails/{jobId}")
    @PreAuthorize("isAuthenticated() and hasRole('HR Manager')")
    public ResponseEntity<Map<String, Object>> getJobDetailsByHRAndJobId(@PathVariable long jobId)
    {
    	Users users = iTokenServices.getCurrentUserObj();
    	Jobs jobDetails = null;
    	try {
    		jobDetails = iJobService.findByCreatedByAndHrId(users, jobId).get();
		} catch (Exception e) {
			jobDetails = null;
		}
    	Map<String, Object> response = new HashMap<>();
    	if (jobDetails!=null) 
    	{
    		response.put("success", true);
			response.put("message", jobDetails);
		}
    	else 
    	{
    		response.put("success", false);
			response.put("message","Post is'nt Present.");
		}
		return  ResponseEntity.ok(response);
    }
    
    @PatchMapping("/doUpdateInJob/{jobId}")
    @PreAuthorize("isAuthenticated() and hasRole('HR Manager')")
    public ResponseEntity<Map<String, Object>> doUpdateInJobDetailsByHRAndJobId(@Valid @RequestBody Jobs job, @PathVariable long jobId)
    {
    	Users users = iTokenServices.getCurrentUserObj();
    	Map<String, Object> response = new HashMap<>();
    	if (Long.valueOf(jobId)!=null && job != null) 
    	{
    		boolean status = false;
			try 
			{
				status = iJobService.updateJob(users, job, jobId);
				response.put("success", status);
				if (status) 
				{
					response.put("message","Job Details update Successfully.");
				} 
				else 
				{
					response.put("message","Job Details is'nt updated due to some error.");
				}
			} 
			catch (Exception e) 
			{
				response.put("success", false);
				response.put("message","Job Details is'nt updated due to some error.");
			}
		} 
    	else 
    	{
    		response.put("success", false);
			response.put("message","Provide a valid Post Id & It's Payload.");
		}
    	return ResponseEntity.ok(response);
    }
}
