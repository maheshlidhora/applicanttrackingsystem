package com.newrise.applicanttrackingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.repository.RolesRepository;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;
import com.newrise.applicanttrackingsystem.services.IUserServices;

@RestController
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardController 
{
	@Autowired
	private IUserServices iUserServices;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
    private UsersRepository usersRepository;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public String getAdminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

    @GetMapping("/hr")
    @PreAuthorize("hasRole('HR Manager')")
    public String getHRDashboard() {
        return "Welcome to the HR Manager Dashboard!";
    }

    @GetMapping("/interviewer")
    @PreAuthorize("hasRole('Interviewer')")
    public String getInterviewerDashboard() {
        return "Welcome to the Interviewer Dashboard!";
    }
    
    @GetMapping("/candidate")
    @PreAuthorize("hasRole('Candidate')")
    public String getCandidateDashboard() {
        return "Welcome to the Candidate Dashboard!";
    }
}
