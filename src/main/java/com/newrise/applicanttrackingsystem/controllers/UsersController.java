package com.newrise.applicanttrackingsystem.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.Roles;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.RolesRepository;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;
import com.newrise.applicanttrackingsystem.services.IOtpService;
import com.newrise.applicanttrackingsystem.services.IUserServices;

import jakarta.validation.Valid;


@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UsersController 
{
	@Autowired
	private IUserServices iUserServices;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
    private UsersRepository usersRepository;
	@Autowired
	private IOtpService iOtpService;

	
	@GetMapping("/")
	public String getUserIndex()
	{
		return "User-Index";
	}
	
	@PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody Users users) {
        Map<String, Object> response = new HashMap<>();
        if (users.getEmail() == null || users.getPassword() == null) {
            response.put("success", false);
            response.put("message", "Email and password are required.");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            // Authenticate User and Generating Token
            String token = iUserServices.varifyUser(users);
            if (token == null) {
                response.put("success", false);
                response.put("message", "Invalid email or password.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            Users authenticatedUser = usersRepository.findByEmail(users.getEmail()).orElseThrow(
                    () -> new RuntimeException("User not found after authentication.")
            );
            Set<String> roles = authenticatedUser.getRoles().stream()
                    .map(r -> r.getRoleName())
                    .collect(Collectors.toSet());
            // Final Response
            response.put("success", true);
            response.put("token", token);
            response.put("user", Map.of(
                    "id", authenticatedUser.getUserId(),
                    "userFullName", authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName(),
                    "firstName", authenticatedUser.getFirstName(),
                    "lastName", authenticatedUser.getLastName(),
                    "email", authenticatedUser.getEmail(),
                    "mobileNo", authenticatedUser.getMobileNo(),
                    "userType", authenticatedUser.getUserType(),
                    "roles", roles
            ));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("success", false);
            response.put("message", "Login failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
	
	@PostMapping("/register")
    public ResponseEntity<Map<String, Object>> getUserSignup(@RequestBody Users users) 
	{
        Map<String, Object> response = new HashMap<>();
        // Validation of Email Format
        if (users.getEmail() == null || !isValidEmail(users.getEmail())) 
        {
            response.put("success", false);
            response.put("message", "Invalid email format. Please provide a valid email.");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Validation of Mobile No Format
        if (users.getMobileNo() == null || !isValidContact(users.getMobileNo())) 
        {
            response.put("success", false);
            response.put("message", "Invalid contact format. Please provide a valid Contact.");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Check the Mobile No is already present in our Database or Not?
        if (usersRepository.findByEmail(users.getMobileNo()).isPresent()) 
        {
            response.put("success", false);
            response.put("message", "This contact is already registered. Please choose another contact.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        
        // Check the email is already present in our Database or Not?
        if (usersRepository.findByEmail(users.getEmail()).isPresent()) 
        {
            response.put("success", false);
            response.put("message", "This email is already registered. Please choose another email.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        // Password Validation
        if (users.getPassword() == null || !isValidPassword(users.getPassword())) 
        {
            response.put("success", false);
            response.put("message", "Password must be 6 to 15 characters long and include at least one uppercase letter, "
            		+ "one lowercase letter, one digit, and one special character.");
            return ResponseEntity.badRequest().body(response);
        }
        // Role Validation
        if (users.getRoles() == null || users.getRoles().isEmpty()) 
        {
            response.put("success", false);
            response.put("message", "No roles provided. Registration failed.");
            return ResponseEntity.badRequest().body(response);
        }

        // Allowing Registration only for "HR Manager" and "Candidate".
        Set<Roles> filteredRoles = users.getRoles().stream()
                .filter(role -> {
                    String roleName = role.getRoleName();
                    return roleName.equalsIgnoreCase("HR Manager") || roleName.equalsIgnoreCase("Candidate");
                })
                .collect(Collectors.toSet());
        if (filteredRoles.isEmpty()) 
        {
            response.put("success", false);
            response.put("message", "Registration allowed only for HR Manager or Candidate roles.");
            return ResponseEntity.badRequest().body(response);
        }

        // Check the role is already present in our Database or Not? And Fetch their roleId to setup with the user.
        Set<Roles> resolvedRoles = new HashSet<>();
        try 
        {
            for (Roles role : filteredRoles) 
            {
                Roles fetchedRole = rolesRepository.findByRoleName(role.getRoleName())
                        .orElseThrow(() -> new RuntimeException("Role '" + role.getRoleName() + "' does not exist."));
                resolvedRoles.add(fetchedRole);
            }
        } 
        catch (RuntimeException ex) 
        {
            response.put("success", false);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        users.setRoles(resolvedRoles);

        // Finally we are registering the user.
        String result = iUserServices.registerUserDetails(users);
        response.put("success", true);
        response.put("message", result);
        response.put("userEmail", users.getEmail());
        return ResponseEntity.ok(response);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String password) {
        // Regex breakdown:
        // (?=.*[a-z])       At least one lowercase
        // (?=.*[A-Z])       At least one uppercase
        // (?=.*\\d)         At least one digit
        // (?=.*[@$!%*?&])   At least one special char
        // .{6,15}           Length between 6 and 15
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,15}$";
        return Pattern.matches(passwordRegex, password);
    }
    
    public static boolean isValidContact(String contact) 
    {
        if (contact == null || !contact.matches("^[6-9]\\d{9}$")) 
        {
            return false;
        }
        return true;
    }
    
    @PostMapping("/generateOtp")
    public String generateOtpForUser(@RequestBody Users user) 
    {
		return iOtpService.generateOtp(user.getEmail())?"OTP is Generate":"User not found with given email or phone.";
    }
    
    @PostMapping("/verifyOtp")
    public String verifyOtpForUser(@RequestBody Users user) 
    {
    	Users claimingUser = user;
    	if (claimingUser!=null  && claimingUser.getOtpCode()!=null) 
    	{
    		try {
        		if (usersRepository.findByEmail(claimingUser.getEmail()).get().getEmail().equalsIgnoreCase(claimingUser.getEmail())) 
        		{
        			boolean status = iOtpService.verifyOtp(claimingUser.getEmail(), claimingUser.getOtpCode());
        			if (status) 
        			{
    					return "User is verified by OTP";
    				}
        			return "OTP doesn't match. Please provide a valid OTP";
    			} 
			} catch (Exception e) {
				return "User not found..!!";
			}
		}
    	return "Please provide a valid Email or OTP that can't be null";
    }
    
    @DeleteMapping("/deleteUser")
	@PreAuthorize("hasRole('Admin')")
    public String deleteUserDetails(@RequestBody Users user)
    {
    	return iUserServices.deleteUser(user);
    }
    
    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('Admin')")
    public List<Users> getAllUsersDetails()
    {
    	return iUserServices.allUsers();
    }
    
    @PostMapping("/findUser")
    @PreAuthorize("hasRole('Admin')")
    public Users findSingleUser(@RequestBody Users user) 
    {
    	return iUserServices.findUser(user.getEmail());
    }
    
    @PatchMapping("/disableUser")
    @PreAuthorize("hasRole('Admin')")
    public String disableUserLogin(@RequestBody Users user) 
    {
    	return iUserServices.disableUser(user.getEmail());
    }
    
    @PatchMapping("/enableUser")
    @PreAuthorize("hasRole('Admin')")
    public String enableUserLogin(@RequestBody Users user) 
    {
    	return iUserServices.enableUser(user.getEmail());
    }
}