package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "post_job")
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

	@ElementCollection
	@CollectionTable(name = "postjob_skills", joinColumns = @JoinColumn(name = "postjob_id"))
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private Users user;

}
