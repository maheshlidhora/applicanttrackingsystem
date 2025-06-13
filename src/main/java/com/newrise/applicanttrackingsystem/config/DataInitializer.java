package com.newrise.applicanttrackingsystem.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.newrise.applicanttrackingsystem.entities.Roles;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.RolesRepository;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;
import com.newrise.applicanttrackingsystem.utils.ColorPrinter;

@Component
public class DataInitializer implements CommandLineRunner
{
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private UsersRepository usersRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
	
	@Override
	public void run(String... args) throws Exception 
	{
		// Creation of Predefined Roles:
        List<String> predefinedRoles = Arrays.asList("Admin", "HR Manager", "Interviewer", "Candidate");
        Map<String, Roles> savedRoles = new HashMap<>();
        for (String roleName : predefinedRoles) {
            Roles role = rolesRepository.findByRoleName(roleName)
                    .orElseGet(() -> {
                        Roles newRole = new Roles();
                        newRole.setRoleName(roleName);
                        newRole.setUsers(new HashSet<>());
                        return rolesRepository.save(newRole);
                    });
            savedRoles.put(roleName, role);
        }

        // Creation of Predefined Admin User:
        String adminEmail = "admin@nrt.com";
        if (usersRepository.findByEmail(adminEmail).isEmpty()) {
            Users adminUser = new Users();
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(bCryptPasswordEncoder.encode("admin@123"));
            adminUser.setRoles(Set.of(savedRoles.get("Admin"))); 
            adminUser.setContact("8516030885");
            adminUser.setVerified(true);
            usersRepository.save(adminUser);
            ColorPrinter.printlnGreen("Default Admin User Created..!!");
        }
        ColorPrinter.printlnGreen("Predefined roles are Initialized..!!");
	}
}
