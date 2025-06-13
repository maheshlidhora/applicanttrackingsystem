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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Hr_postJob")
public class Hrpostjob {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "Fast_Name")
	private String fastname;
	
	@Column(name = "Last_Name")
	private String lastname;
	
	@Column(name = "Hr_Email")
	private String email;
	
	@Column(name = "Department")
	private String department;
	
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
	
//	
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private List<PostJob> postJobs;
}
