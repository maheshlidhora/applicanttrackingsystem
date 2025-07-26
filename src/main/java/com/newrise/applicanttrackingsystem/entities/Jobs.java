package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Entity
@Table(name = "Jobs")
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
    private Double salary;

    @NotNull(message = "Openings count is required")
    @Column(nullable = false)
    private Integer openings;

	@Column(name = "createdAt", updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime postedDate;

    @NotNull(message = "Closing date is required")
    @Column(nullable = false)
    private LocalDate closingDate;

    @Column(nullable = false)
    private boolean isActive = true;    
    
    // HR Manager who created this job
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdBy", nullable = false)
    private Users createdBy;

    // All candidate applications for this job
    @JsonIgnore
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobApplications> jobApplications;
    
    @PrePersist
    public void onCreate() {
        this.postedDate = LocalDateTime.now();
    }	
    
    //	*************************************  Getter, Setter & Constructors  *************************************

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

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getOpenings() {
		return openings;
	}

	public void setOpenings(Integer openings) {
		this.openings = openings;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDateTime postedDate) {
		this.postedDate = postedDate;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
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
			@NotBlank(message = "Experience is required") String experience,
			@NotNull(message = "Salary is required") Double salary,
			@NotNull(message = "Openings count is required") Integer openings, LocalDateTime createdAt,
			LocalDateTime postedDate, LocalDate closingDate, boolean isActive, Users createdBy,
			Set<JobApplications> jobApplications) {
		super();
		this.jobId = jobId;
		this.title = title;
		this.description = description;
		this.department = department;
		this.location = location;
		this.experience = experience;
		this.salary = salary;
		this.openings = openings;
		this.createdAt = createdAt;
		this.postedDate = postedDate;
		this.closingDate = closingDate;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.jobApplications = jobApplications;
	}

	public Jobs() {
		super();
		// TODO Auto-generated constructor stub
	}
}
