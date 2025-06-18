package com.newrise.applicanttrackingsystem.servicesimpl;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Permissions;
import com.newrise.applicanttrackingsystem.entities.Roles;
import com.newrise.applicanttrackingsystem.repository.PermissionsRepository;
import com.newrise.applicanttrackingsystem.repository.RolesRepository;
import com.newrise.applicanttrackingsystem.services.IRoleServices;

@Service
public class RoleServicesImpl implements IRoleServices
{
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private PermissionsRepository permissionsRepository;
	
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
		System.err.println(updatedRole.getPermissions());
		try 
		{
			Optional<Roles> optionalRole = rolesRepository.findById(id);
			System.err.println(optionalRole.get().getRoleName());
			if (optionalRole.isPresent()) 
			{
				Roles existingRole = optionalRole.get();
				existingRole.setRoleName(updatedRole.getRoleName());

				// Resolve permissions by name
	            Set<Permissions> resolvedPermissions = new HashSet<>();
	            for (Permissions p : updatedRole.getPermissions()) {
	                Optional<Permissions> permission = permissionsRepository.findByPermissionName(p.getPermissionName());
	                if (permission != null) {
	                    resolvedPermissions.add(permission.get());
	                }
	            }
	            existingRole.setPermissions(resolvedPermissions);
				rolesRepository.save(existingRole);
				return "Role updated successfully!";
			} 
			else 
			{
				return "Role not found or Permission Doesn't Match..!!";
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
