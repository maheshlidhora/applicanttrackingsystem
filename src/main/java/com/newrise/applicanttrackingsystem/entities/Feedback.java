package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "Feedback")
public class Feedback 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long feedbackId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "interview_id", nullable = false)
	private Interview interview;

	@Column(length = 500)
	private String comments;
	
	@Min(1)
	@Max(5)
	@Column(nullable=false)
	private int rating; //	1–5 scale

	@Column(nullable = false, updatable = false)
	private LocalDateTime submittedAt = LocalDateTime.now();

	//	*************************************  Getter, Setter & Constructors  *************************************
	
	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}

	public Feedback(Long feedbackId, Interview interview, String comments, int rating, LocalDateTime submittedAt) {
		super();
		this.feedbackId = feedbackId;
		this.interview = interview;
		this.comments = comments;
		this.rating = rating;
		this.submittedAt = submittedAt;
	}

	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}
}
