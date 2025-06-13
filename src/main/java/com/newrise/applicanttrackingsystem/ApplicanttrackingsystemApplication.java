package com.newrise.applicanttrackingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicanttrackingsystemApplication {

	public static void main(String[] args) {
		System.err.println("NRT-ATS is Started...");
		SpringApplication.run(ApplicanttrackingsystemApplication.class, args);
		System.err.println("NRT-ATS is available on Server...");
	}

}
