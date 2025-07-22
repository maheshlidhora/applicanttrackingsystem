package com.newrise.applicanttrackingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

// 	This annotation Enables pagination and sorting support in Spring MVC controllers.
//@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class ApplicanttrackingsystemApplication {

	public static void main(String[] args) {
		System.err.println("NRT-ATS is Started..!!");
		SpringApplication.run(ApplicanttrackingsystemApplication.class, args);
		System.err.println("NRT-ATS is available on Server..!!");
	}

}
