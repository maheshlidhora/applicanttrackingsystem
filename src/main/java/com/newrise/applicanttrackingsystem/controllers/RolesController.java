package com.newrise.applicanttrackingsystem.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.Roles;
import com.newrise.applicanttrackingsystem.services.IRoleServices;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class RolesController 
{
	@Autowired
	private IRoleServices iRoleServices;
	
	@PostMapping("/addRole")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Map<String, Object>> addRoleDetails(@RequestBody Roles roles) 
	{
	    Map<String, Object> response = new HashMap<>();
	    try {
	        if (iRoleServices.findRoleDetails(roles.getRoleName()).isPresent()) {
	            response.put("success", false);
	            response.put("message", "Role '" + roles.getRoleName() + "' already exists. Please choose a different name.");
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	        }
	        String result = iRoleServices.addRole(roles);
	        response.put("success", true);
	        response.put("message", result);
	        return ResponseEntity.ok(response);
	    } catch (Exception ex) {
	        response.put("success", false);
	        response.put("message", "Failed to add role: " + ex.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	@GetMapping("/getAllRoles")
	@PreAuthorize("hasRole('Admin')")
	public List<Roles> getAllRoleDetails(){
		return iRoleServices.fineAllRoles();
	}
	
	@DeleteMapping("/deleteRole/{id}")
	@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteRoleDetails(@PathVariable long id) {
        String response = iRoleServices.deleteRole(id);
        if ("Role is Deleted!!".equalsIgnoreCase(response)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Role with ID " + id + " deleted successfully.");
        } else if ("Role is not Present!!".equalsIgnoreCase(response)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Role with ID " + id + " not found.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while deleting role with ID " + id + ".");
        }
    }
	
	@PatchMapping("/updateRole/{id}")
	@PreAuthorize("hasRole('Admin')")
	public String updateRoleDetails(@PathVariable long id, @RequestBody Roles roles)
	{
		return iRoleServices.updateRole(id, roles);
	}
}
