
package com.newrise.applicanttrackingsystem.services;

public interface IOtpService 
{
	boolean generateOtp(String email);
	boolean verifyOtp(String email, String otp);
}
