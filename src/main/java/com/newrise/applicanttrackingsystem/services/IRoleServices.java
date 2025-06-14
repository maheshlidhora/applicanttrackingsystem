package com.newrise.applicanttrackingsystem.services;

import java.util.List;
import java.util.Optional;

import com.newrise.applicanttrackingsystem.entities.Roles;

public interface IRoleServices 
{
	public String addRole(Roles roles);
	public String updateRole(long id, Roles updatedRole);
	public String deleteRole(long id);
	public Optional<Roles> findRoleDetails(String roleName);
	public List<Roles> fineAllRoles();
}
