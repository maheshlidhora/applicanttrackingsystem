package com.newrise.applicanttrackingsystem.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ApplicationStatus")
public class ApplicationStatus 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "statusId", unique = true)
	private long statusId;

	@Column(name = "statusName", unique = true, length = 75)
	private String statusName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "applicationStatus", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobApplications> jobApplications;
	
	//	*************************************  Getter, Setter & Constructors  *************************************

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Set<JobApplications> getJobApplications() {
		return jobApplications;
	}

	public void setJobApplications(Set<JobApplications> jobApplications) {
		this.jobApplications = jobApplications;
	}

	public ApplicationStatus(long statusId, String statusName, Set<JobApplications> jobApplications) {
		super();
		this.statusId = statusId;
		this.statusName = statusName;
		this.jobApplications = jobApplications;
	}

	public ApplicationStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
}
