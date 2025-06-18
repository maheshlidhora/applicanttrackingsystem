package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDateTime;
import java.util.Set;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Entity
@Table(name = "Jobs")
@Builder
public class Jobs 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jobId;

    @NotBlank(message = "Job title is required")
    @Column(nullable = false, length = 150)
    private String title;

    @NotBlank(message = "Job description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Department is required")
    @Column(nullable = false, length = 100)
    private String department;

    @NotBlank(message = "Location is required")
    @Column(nullable = false, length = 100)
    private String location;

    @NotBlank(message = "Experience is required")
    @Column(nullable = false, length = 100)
    private String experience;
    
    @NotNull(message = "Salary is required")
    @Column(nullable = false)
    private double salary;

    @NotNull(message = "Openings count is required")
    @Column(nullable = false)
    private int openings;

    @Column(nullable = false, updatable = false)
    private LocalDateTime postedDate;

    @Column
    private LocalDateTime closingDate;

    @Column(nullable = false)
    private boolean isActive = true;    
    
    // HR Manager who created this job
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdBy", nullable = false)
    private Users createdBy;

    // All candidate applications for this job
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobApplications> jobApplications;
    
    @PrePersist
    public void onCreate() {
        this.postedDate = LocalDateTime.now();
    }

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public int getOpenings() {
		return openings;
	}

	public void setOpenings(Integer openings) {
		this.openings = openings;
	}

	public LocalDateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDateTime postedDate) {
		this.postedDate = postedDate;
	}

	public LocalDateTime getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDateTime closingDate) {
		this.closingDate = closingDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Users getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}

	public Set<JobApplications> getJobApplications() {
		return jobApplications;
	}

	public void setJobApplications(Set<JobApplications> jobApplications) {
		this.jobApplications = jobApplications;
	}

	public Jobs(long jobId, @NotBlank(message = "Job title is required") String title,
			@NotBlank(message = "Job description is required") String description,
			@NotBlank(message = "Department is required") String department,
			@NotBlank(message = "Location is required") String location,
			@NotNull(message = "Salary is required") Double salary,
			@NotNull(message = "Openings count is required") Integer openings, LocalDateTime postedDate,
			LocalDateTime closingDate, boolean isActive, Users createdBy, Set<JobApplications> jobApplications) {
		super();
		this.jobId = jobId;
		this.title = title;
		this.description = description;
		this.department = department;
		this.location = location;
		this.salary = salary;
		this.openings = openings;
		this.postedDate = postedDate;
		this.closingDate = closingDate;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.jobApplications = jobApplications;
	}

	public Jobs() {
		super();
	}
}
