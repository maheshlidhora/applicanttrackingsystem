package com.newrise.applicanttrackingsystem.entities;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Permissions")
public class Permissions 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permissionId", unique = true)
    private long permissionId;

    @Column(name = "permissionName", nullable = false, unique = true, length = 100)
    private String permissionName;
    
    @Column(name = "slug", unique = true, length = 100)
    private String slug;
    
    @Column(name = "permissionGroup", length = 100)
    private String permissionGroup;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    private Set<Roles> roles;
    
	@Column(name = "createdAt", updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	//	*************************************  Getter, Setter & Constructors  *************************************

	public long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getPermissionGroup() {
		return permissionGroup;
	}

	public void setPermissionGroup(String permissionGroup) {
		this.permissionGroup = permissionGroup;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Permissions(long permissionId, String permissionName, String slug, String permissionGroup, Set<Roles> roles,
			LocalDateTime createdAt) {
		super();
		this.permissionId = permissionId;
		this.permissionName = permissionName;
		this.slug = slug;
		this.permissionGroup = permissionGroup;
		this.roles = roles;
		this.createdAt = createdAt;
	}

	public Permissions() {
		super();
	}
}