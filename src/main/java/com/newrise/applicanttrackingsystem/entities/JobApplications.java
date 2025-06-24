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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jobApplications")
public class JobApplications 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long applicationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id", nullable = false)
    private Jobs job;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Users candidate;

    @Column(nullable = false)
    private String applicationStatus = "APPLIED"; // APPLIED, WITHDRAWN

    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @PrePersist
    public void onApply() {
        this.appliedAt = LocalDateTime.now();
    }

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

	public Users getCandidate() {
		return candidate;
	}

	public void setCandidate(Users candidate) {
		this.candidate = candidate;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public LocalDateTime getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(LocalDateTime appliedAt) {
		this.appliedAt = appliedAt;
	}

	public JobApplications(long applicationId, Jobs job, Users candidate, String applicationStatus,
			LocalDateTime appliedAt) {
		super();
		this.applicationId = applicationId;
		this.job = job;
		this.candidate = candidate;
		this.applicationStatus = applicationStatus;
		this.appliedAt = appliedAt;
	}

	public JobApplications() {
		super();
	}
}
