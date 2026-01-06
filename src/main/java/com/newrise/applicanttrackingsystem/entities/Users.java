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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Users 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId", unique = true)
	private long userId;

	@Column(name = "firstName", nullable = false, length = 75)
	private String firstName;
	
	@Column(name = "lastName", nullable = false, length = 75)
	private String lastName;
	
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
	
	@Column(name = "userType", nullable = false, length = 250)
	private String userType;

	@Column(name = "mobileNo", nullable = false, unique = true, length = 10)
	@Size(min = 10, max = 10, message = "Contact number must be 10 digits")
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact number must contain only digits")
	private String mobileNo;

	@Column(name = "otpCode", length = 6)
	private String otpCode;

	@Column(name = "otpExpiry")
	private LocalDateTime otpExpiry;
	
	@Column(name = "createdAt", updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "isVerified")
	private boolean isVerified;
	
	@Column(name = "isBlocked", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean isBlocked = false;

	// Jobs created by HR Manager
	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Jobs> createdJobs;

	// Jobs applied to by Candidate
	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<JobApplications> jobApplications;
	
	// Tokens issued for this user
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Token> tokens;
	
	@OneToMany(mappedBy = "interviewer", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Interview> interviewsAsInterviewer;

}
