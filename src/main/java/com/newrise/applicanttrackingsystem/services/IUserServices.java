package com.newrise.applicanttrackingsystem.services;

import java.util.Optional;

import com.newrise.applicanttrackingsystem.entities.Users;

<<<<<<< HEAD:src/main/java/com/newrise/applicanttrackingsystem/services/UserServices.java
public interface UserServices 
=======
import io.jsonwebtoken.JwtBuilder;

public interface IUserServices 
>>>>>>> d7282b574a48ffe8b4885e02b06454390eb87a14:src/main/java/com/newrise/applicanttrackingsystem/services/IUserServices.java
{
	public Optional<Users> findUserDetails(String email, String password);
}
