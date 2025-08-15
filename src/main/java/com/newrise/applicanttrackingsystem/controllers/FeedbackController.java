package com.newrise.applicanttrackingsystem.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.Feedback;
import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.services.IFeedbackServices;
import com.newrise.applicanttrackingsystem.services.IInterviewServices;

@RestController
@CrossOrigin("*")
@RequestMapping("/interview/feedback")
public class FeedbackController {
	@Autowired
	private IInterviewServices interviewServices;
	@Autowired
	private IFeedbackServices feedbackServices;

	@PostMapping("/addFeedback")
	@PreAuthorize("isAuthenticated() and (hasRole('HR Manager') or hasRole('Interviewer'))")
	public ResponseEntity<Map<String, Object>> addFeedback(@RequestBody Feedback feedback) 
	{
	    Map<String, Object> response = new HashMap<>();
	    // Validate Interview ID
	    if (feedback.getInterview() == null || feedback.getInterview().getInterviewId() == null) {
	        response.put("message", "Interview ID is required.");
	        return ResponseEntity.badRequest().body(response);
	    }
	    Interview interview = null;
	    try {
	    	interview = interviewServices.fetchinterview(feedback.getInterview().getInterviewId());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	    if (interview == null) {
	        response.put("message", "No interview found for the given Interview ID.");
	        return ResponseEntity.badRequest().body(response);
	    }
	    feedback.setInterview(interview);
	    // Validate Rating
	    int rating = feedback.getRating();
	    if (rating < 1 || rating > 5) {
	        response.put("message", "Rating must be between 1 and 5.");
	        return ResponseEntity.badRequest().body(response);
	    }
	    boolean status = feedbackServices.createFeedback(feedback);
	    if (status) {
	        response.put("message", String.format(
	            "Feedback successfully added for Interview ID: %d",
	            feedback.getInterview().getInterviewId()
	        ));
	        return ResponseEntity.ok(response);
	    } else {
	        response.put("message", "Failed to add feedback due to an internal server error.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
    @GetMapping("getFeedback/{feedbackId}")
    @PreAuthorize("isAuthenticated() and (hasRole('HR Manager') or hasRole('Interviewer'))")
    public ResponseEntity<Map<String, Object>> getFeedback(@PathVariable Long feedbackId) 
    {
        Map<String, Object> response = new HashMap<>();
	    if (feedbackId == null || feedbackId <= 0) {
	        response.put("message", "Feedback ID must be a positive number and cannot be null.");
	        return ResponseEntity.badRequest().body(response);
	    }
        Optional<Feedback> feedbackOpt = feedbackServices.getFeedback(feedbackId);
        if (feedbackOpt.isPresent()) {
            response.put("message", "Feedback retrieved successfully.");
            response.put("data", feedbackOpt.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "No feedback found for the given ID.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @PatchMapping("updateFeedback/{feedbackId}")
    @PreAuthorize("isAuthenticated() and (hasRole('HR Manager') or hasRole('Interviewer'))")
    public ResponseEntity<Map<String, Object>> updateFeedback(@PathVariable Long feedbackId, @RequestBody Feedback feedback) 
    {
        Map<String, Object> response = new HashMap<>();
	    if (feedbackId == null || feedbackId <= 0) {
	        response.put("message", "Feedback ID must be a positive number and cannot be null.");
	        return ResponseEntity.badRequest().body(response);
	    }
        Optional<Feedback> oldFeedback = feedbackServices.getFeedback(feedbackId);
        if (oldFeedback.isPresent()) {
        	Feedback feedbackOpt = oldFeedback.get();
        	feedbackOpt.setRating(feedback.getRating());
        	feedbackOpt.setComments(feedback.getComments());
        	boolean status = feedbackServices.createFeedback(feedbackOpt);
    	    if (status) {
                response.put("message", "Feedback updated successfully.");
                response.put("data", feedbackOpt);
                return ResponseEntity.ok(response);
    	    } else {
    	        response.put("message", "Failed to update feedback due to an internal server error.");
    	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    	    }
        } else {
            response.put("message", "No feedback found for the given ID.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @DeleteMapping("deleteFeedback/{feedbackId}")
    @PreAuthorize("isAuthenticated() and (hasRole('HR Manager') or hasRole('Interviewer'))")
    public ResponseEntity<Map<String, Object>> deleteFeedback(@PathVariable Long feedbackId) 
    {
        Map<String, Object> response = new HashMap<>();
	    if (feedbackId == null || feedbackId <= 0) {
	        response.put("message", "Feedback ID must be a positive number and cannot be null.");
	        return ResponseEntity.badRequest().body(response);
	    }
        Optional<Feedback> feedbackOpt = feedbackServices.getFeedback(feedbackId);
        if (feedbackOpt.isPresent()) {
        	boolean status = feedbackServices.deleteFeedback(feedbackOpt.get());
    	    if (status) {
    	    	response.put("message", "Feedback deleted successfully.");
                return ResponseEntity.ok(response);
    	    } else {
    	        response.put("message", "Failed to delete feedback due to an internal server error.");
    	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    	    }
        } else {
            response.put("message", "No feedback found for the given ID.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
