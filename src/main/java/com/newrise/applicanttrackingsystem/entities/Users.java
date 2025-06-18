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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Entity
@Table(name = "Users")
@Builder
public class Users 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId", unique = true)
	private long userId;

	@Column(name = "email", nullable = false, unique = true, length = 150)
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is required")
	private String email;

	@Column(name = "password", nullable = false, length = 150)
	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password should be at least 6 characters to maximum 15 characters")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "userId") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "roleId") })
	private Set<Roles> roles;

	@Column(name = "contact", nullable = false, unique = true, length = 10)
	@Size(min = 10, max = 10, message = "Contact number must be 10 digits")
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact number must contain only digits")
	private String contact;

	@Column(name = "otpCode", length = 6)
	private String otpCode;

	@Column(name = "otpExpiry")
	private LocalDateTime otpExpiry;

	@Column(name = "isVerified")
	private boolean isVerified;

	// Jobs created by HR Manager
	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
	private Set<Jobs> createdJobs;

	// Jobs applied to by Candidate
	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JobApplications> jobApplications;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public LocalDateTime getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(LocalDateTime otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Set<Jobs> getCreatedJobs() {
		return createdJobs;
	}

	public void setCreatedJobs(Set<Jobs> createdJobs) {
		this.createdJobs = createdJobs;
	}

	public Set<JobApplications> getJobApplications() {
		return jobApplications;
	}

	public void setJobApplications(Set<JobApplications> jobApplications) {
		this.jobApplications = jobApplications;
	}

	public Users(long userId,
			@Email(message = "Email should be valid") @NotBlank(message = "Email is required") String email,
			@NotBlank(message = "Password is required") @Size(min = 6, message = "Password should be at least 6 characters to maximum 15 characters") String password,
			Set<Roles> roles,
			@Size(min = 10, max = 10, message = "Contact number must be 10 digits") @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must contain only digits") String contact,
			String otpCode, LocalDateTime otpExpiry, boolean isVerified, Set<Jobs> createdJobs,
			Set<JobApplications> jobApplications) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.contact = contact;
		this.otpCode = otpCode;
		this.otpExpiry = otpExpiry;
		this.isVerified = isVerified;
		this.createdJobs = createdJobs;
		this.jobApplications = jobApplications;
	}

	public Users() {
		super();
	}
}
