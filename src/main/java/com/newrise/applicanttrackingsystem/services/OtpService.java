
package com.newrise.applicanttrackingsystem.services;

public interface OtpService 
{
	boolean generateOtp(String email);
	boolean verifyOtp(String email, String otp);
}
