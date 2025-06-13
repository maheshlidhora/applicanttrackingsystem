package com.newrise.applicanttrackingsystem.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.newrise.applicanttrackingsystem.entities.Hrpostjob;
import com.newrise.applicanttrackingsystem.repository.HrpostjobRepository;
import com.newrise.applicanttrackingsystem.services.HrpostjobService;

@Service
public class HrpostjobServiceimpl implements HrpostjobService {

	@Autowired
	private HrpostjobRepository hrpostjobRepository;
	
	@Override
	public Hrpostjob inserthr(Hrpostjob hrpostjob) {
		return hrpostjobRepository.save(hrpostjob);
		
		
	}

	@Override
	public Hrpostjob getHrByid(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
