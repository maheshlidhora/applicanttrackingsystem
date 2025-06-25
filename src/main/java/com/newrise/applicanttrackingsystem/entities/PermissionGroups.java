package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PermissionGroups")
public class PermissionGroups 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "groupId", unique = true)
	private long groupId;

	@Column(name = "groupName", unique = true)
	private String groupName;
	
	@Column(name = "slug", unique = true)
	private String slug;
	
	@Column(name = "createdAt", updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	//	*************************************  Getter, Setter & Constructors  *************************************

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public PermissionGroups(long groupId, String groupName, String slug, LocalDateTime createdAt) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.slug = slug;
		this.createdAt = createdAt;
	}

	public PermissionGroups() {
		super();
	}
}
