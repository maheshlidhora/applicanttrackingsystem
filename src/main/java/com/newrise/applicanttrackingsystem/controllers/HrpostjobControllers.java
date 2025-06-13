package com.newrise.applicanttrackingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.Hrpostjob;
import com.newrise.applicanttrackingsystem.services.HrpostjobService;
import com.newrise.applicanttrackingsystem.servicesimpl.HrpostjobServiceimpl;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class HrpostjobControllers {

	@Autowired
	private HrpostjobService hrpostjobService;
	
	@PostMapping("/hrcreated")
	public ResponseEntity<Hrpostjob> insertHr(@RequestBody Hrpostjob hrpostjob) {
		Hrpostjob hrpostjob2 = hrpostjobService.inserthr(hrpostjob);
		return new ResponseEntity<Hrpostjob>(hrpostjob2, HttpStatus.CREATED);
	}
}
