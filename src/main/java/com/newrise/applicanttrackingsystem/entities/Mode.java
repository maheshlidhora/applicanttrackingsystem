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
@Table(name = "Mode")
public class Mode 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "modeId", unique = true)
	private long modeId;

	@Column(name = "modeName", unique = true, length = 75)
	private String modeName; 	//	Online, Offline, On-site, Work from Home
	
	@OneToMany(mappedBy = "mode", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Interview> interviews;

	
	//	*************************************  Getter, Setter & Constructors  *************************************

	public long getModeId() {
		return modeId;
	}

	public void setModeId(long modeId) {
		this.modeId = modeId;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public Set<Interview> getInterviews() {
		return interviews;
	}

	public void setInterviews(Set<Interview> interviews) {
		this.interviews = interviews;
	}

	public Mode(long modeId, String modeName, Set<Interview> interviews) {
		super();
		this.modeId = modeId;
		this.modeName = modeName;
		this.interviews = interviews;
	}

	public Mode() {
		super();
		// TODO Auto-generated constructor stub
	}	
}
