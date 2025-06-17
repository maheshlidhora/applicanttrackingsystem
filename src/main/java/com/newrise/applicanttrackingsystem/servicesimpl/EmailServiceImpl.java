package com.newrise.applicanttrackingsystem.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.services.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService
{
	@Autowired
	private JavaMailSender javaMailSender;

//	@Value("$(spring.mail.username)")
	private String fromMailId = "mahesh.lidhora@gmail.com";
	
	@Override
	public boolean sendEmail(String recipient, String subject, String body) 
	{
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(fromMailId);
		simpleMailMessage.setTo(recipient);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(body);
		javaMailSender.send(simpleMailMessage);
		return true;
	}
	
}
