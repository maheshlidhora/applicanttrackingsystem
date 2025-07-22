package com.newrise.applicanttrackingsystem.servicesimpl;

import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.repository.ApplicationsRepository;
import com.newrise.applicanttrackingsystem.services.IApplicationService;

@Service
public class ApplicationServiceImpl implements IApplicationService
{
	private ApplicationsRepository applicationsRepository;

	@Override
	public boolean applyForJob() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean withdrawFromJob() {
		// TODO Auto-generated method stub
		return false;
	}
}
