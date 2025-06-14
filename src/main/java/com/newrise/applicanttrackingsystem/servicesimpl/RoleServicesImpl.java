package com.newrise.applicanttrackingsystem.servicesimpl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Roles;
import com.newrise.applicanttrackingsystem.repository.RolesRepository;
import com.newrise.applicanttrackingsystem.services.IRoleServices;

@Service
public class RoleServicesImpl implements IRoleServices
{
	@Autowired
	private RolesRepository rolesRepository;
	@Override
	public String addRole(Roles roles) 
	{
		if (roles != null) 
		{
			rolesRepository.save(roles);
			return "Role Registered Successfully!!";
		} 
		else 
		{
			return "Role Not Registered!!";
		}
	}

	@Override
	public String updateRole(long id, Roles updatedRole) 
	{
		try 
		{
			Optional<Roles> optionalRole = rolesRepository.findById(id);
			if (optionalRole.get().getRoleId()==id) 
			{
				Roles existingRole = optionalRole.get();
				existingRole.setRoleName(updatedRole.getRoleName());
				rolesRepository.save(existingRole);
				return "Role updated successfully!";
			} 
			else 
			{
				return "Role not found!";
			}
		} 
		catch (Exception e) 
		{
			return "Role not found!";
		}
	}

	@Override
	public String deleteRole(long id) 
	{
		try 
		{
			if (rolesRepository.findById(id).get().getRoleId()==id) 
			{
				rolesRepository.deleteById(id);				
			}
			return "Role is Deleted!!";
		} 
		catch (Exception e) 
		{
			return "Role is not Present!!";
		}
	}

	@Override
	public Optional<Roles> findRoleDetails(String roleName) 
	{
		return rolesRepository.findByRoleName(roleName);
	}
	
	@Override
	public List<Roles> fineAllRoles() 
	{
		return rolesRepository.findAll();
	}
}
