package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostJob {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Department")
	private String department;
	
	@Column(name = "Location")
	private String location;
	
	@Column(name = "ExperienceRequired")
	private String experienceRequired;
	
	@Column(name = "Salary")
	private long salary;
	
	@Column(name = "Skill")
	private List<String> skill;
	
	@Column(name = "Description")
	private String description;
	
	private LocalDateTime localDateTime;
	
	private LocalDateTime updatedAt;
	@PrePersist
	protected void onCreate() {
		this.localDateTime = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
	    this.updatedAt = LocalDateTime.now();
	}
	
//	@ManyToMany(mappedBy = "postJobs" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private List<Users> user;
//	
	
}
