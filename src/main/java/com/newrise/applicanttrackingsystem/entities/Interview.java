package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "Interview")
public class Interview 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long interviewId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "job_application_id", nullable = false)
	private JobApplications jobApplication;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "interviewer_id", nullable = false)
	private Users interviewer;

	@Column(nullable = false)
	private LocalDateTime scheduledAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mode_id", nullable = false)
	private Mode mode; // Online, Offline, On-site, WFH≠

	@Column(name = "remarks", length = 1000)
	private String remarks;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Feedback> feedbackList;

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	//	*************************************  Getter, Setter & Constructors  *************************************
	
	public Long getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Long interviewId) {
		this.interviewId = interviewId;
	}

	public JobApplications getJobApplication() {
		return jobApplication;
	}

	public void setJobApplication(JobApplications jobApplication) {
		this.jobApplication = jobApplication;
	}

	public Users getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(Users interviewer) {
		this.interviewer = interviewer;
	}

	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(Set<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}

	public Interview(Long interviewId, JobApplications jobApplication, Users interviewer, LocalDateTime scheduledAt,
			Mode mode, String remarks, LocalDateTime createdAt, LocalDateTime updatedAt, Set<Feedback> feedbackList) {
		super();
		this.interviewId = interviewId;
		this.jobApplication = jobApplication;
		this.interviewer = interviewer;
		this.scheduledAt = scheduledAt;
		this.mode = mode;
		this.remarks = remarks;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.feedbackList = feedbackList;
	}

	public Interview() {
		super();
		// TODO Auto-generated constructor stub
	}
}
