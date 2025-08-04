package com.newrise.applicanttrackingsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	public Mode(long modeId, String modeName) {
		super();
		this.modeId = modeId;
		this.modeName = modeName;
	}

	public Mode() {
		super();
		// TODO Auto-generated constructor stub
	}
}
