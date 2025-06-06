package com.newrise.applicanttrackingsystem.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	public Roles(long roleId, String roleName, Set<Users> users) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.users = users;
	}

	public Roles() {
		super();
	}
}
