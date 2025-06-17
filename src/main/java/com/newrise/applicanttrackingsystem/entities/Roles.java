package com.newrise.applicanttrackingsystem.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roleId", unique = true)
	private long roleId;

	@Column(name = "roleName", unique = true)
	private String roleName; // "Admin", "HR Manager", "Interviewer", "Candidate"

	@JsonIgnore // To ignore infinite recursion (circular reference) in your JSON response.
	@ManyToMany (mappedBy = "roles",fetch = FetchType.EAGER)
	private Set<Users> users;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(
	    name = "role_permissions",
	    joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"),
	    inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "permissionId")
	)
	private Set<Permissions> permissions;


	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<Users> getUsers() {
		return users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}

	public Set<Permissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permissions> permissions) {
		this.permissions = permissions;
	}

	public Roles(long roleId, String roleName, Set<Users> users, Set<Permissions> permissions) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.users = users;
		this.permissions = permissions;
	}

	public Roles() {
		super();
	}
}
