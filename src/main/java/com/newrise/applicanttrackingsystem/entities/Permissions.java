package com.newrise.applicanttrackingsystem.entities;

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


    @JsonIgnore
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    private Set<Roles> roles;


	public long getPermissionId() {
		return permissionId;
	}


	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}


	public String getPermissionName() {
		return permissionName;
	}


	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}


	public Set<Roles> getRoles() {
		return roles;
	}


	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}


	public Permissions(Long permissionId, String permissionName, Set<Roles> roles) {
		super();
		this.permissionId = permissionId;
		this.permissionName = permissionName;
		this.roles = roles;
	}


	public Permissions() {
		super();
	}
}