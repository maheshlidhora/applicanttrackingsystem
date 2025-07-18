package com.newrise.applicanttrackingsystem.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.repository.JobsRepository;
import com.newrise.applicanttrackingsystem.services.IJobService;

@Service
public class JobServiceImpl implements IJobService 
{
	@Autowired
	private JobsRepository jobsRepository;

	@Override
	public boolean createPost(Jobs job) 
	{
		try 
		{
			jobsRepository.save(job);
			return true;
		} 
		catch (Exception e) {
			return false;
		}
	}

}
