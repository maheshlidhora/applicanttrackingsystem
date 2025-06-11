package com.newrise.applicanttrackingsystem.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "Users")
public class Users 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId", unique = true)
	private long userId;

	@Column(name = "email", nullable = false, unique = true, length = 150)
	@Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
	private String email;

	@Column(name = "password", nullable = false, length = 150)
	@NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters to maximum 15 characters")
	private String password;
	
	@ManyToMany (fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable (name = "user_roles",
	joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "userId")
	},inverseJoinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "roleId") 
	})
	private Set<Roles> roles;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public Users(long userId, String email, String password, Set<Roles> roles) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	public Users() {
		super();
	}
}
