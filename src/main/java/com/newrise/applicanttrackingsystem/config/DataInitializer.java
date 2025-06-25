package com.newrise.applicanttrackingsystem.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.newrise.applicanttrackingsystem.entities.Permissions;
import com.newrise.applicanttrackingsystem.entities.Roles;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.PermissionsRepository;
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
	@Autowired
	private PermissionsRepository permissionsRepository;
	
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
                        newRole.setSlug(
                        		
                        		roleName.replaceAll("\\s+", "-").toLowerCase()
                        		
                        		);
                        newRole.setUsers(new HashSet<>());
                        return rolesRepository.save(newRole);
                    });
            savedRoles.put(roleName, role);
        }

        // Predefined Permissions
        List<String> permissionNames = Arrays.asList("CREATE", "VIEW", "UPDATE", "DELETE");
        Map<String, Permissions> allPermissions = new HashMap<>();
        for (String permName : permissionNames) {
            Permissions permission = permissionsRepository.findByPermissionName(permName)
                    .orElseGet(() -> {
                        Permissions newPerm = new Permissions();
                        newPerm.setPermissionName(permName);
                        newPerm.setSlug(
                        		
                        		permName.replaceAll("\\s+", "-").toLowerCase()
                        		
                        		);
                        return permissionsRepository.save(newPerm);
                    });
            allPermissions.put(permName, permission);
        }
        
        // Converting Map values into Set
        Set<Permissions> allPermissionsIntoSetFormat = new HashSet<>(allPermissions.values());
        
        // Assigning all permissions to Admin
        Roles adminRole = savedRoles.get("Admin");
        adminRole.setPermissions(allPermissionsIntoSetFormat);
        rolesRepository.save(adminRole);
        
        // Creation of Predefined Admin User:
        String adminEmail = "admin@nrt.com";
        if (usersRepository.findByEmail(adminEmail).isEmpty()) {
            Users adminUser = new Users();
            adminUser.setFirstName("New Rise");
            adminUser.setLastName("Admin");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(bCryptPasswordEncoder.encode("admin@123"));
            adminUser.setRoles(Set.of(savedRoles.get("Admin"))); 
            adminUser.setUserType(adminUser.getRoles().stream().map(Roles::getRoleName).collect(Collectors.joining(" + ")));
            adminUser.setMobileNo("9876543210");
            adminUser.setVerified(true);
            usersRepository.save(adminUser);
            ColorPrinter.printlnGreen("Default Admin User Created..!!");
        }
        ColorPrinter.printlnGreen("Predefined roles are Initialized..!!");
	}
}
