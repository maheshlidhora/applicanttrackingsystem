package com.newrise.applicanttrackingsystem.services;

public interface IEmailService 
{
	public boolean sendEmail(String recipient, String body, String subject);
}
