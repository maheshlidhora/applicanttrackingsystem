package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.JobsRepository;
import com.newrise.applicanttrackingsystem.services.IJobService;

@Service
public class JobServiceImpl implements IJobService 
{
	@Autowired
	private JobsRepository jobsRepository;

	@Override
	public boolean createJobs(Jobs job) 
	{
		try 
		{
			jobsRepository.save(job);
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	@Override
	public Page<Jobs> getAllJobsPaginated(Pageable pageable) 
	{
		return jobsRepository.findAll(pageable);
	}

	@Override
	public Page<Jobs> getAllJobsToHR(Users createdBy, Pageable pageable) 
	{
		return jobsRepository.findByCreatedBy(createdBy, pageable);
	}
	
	@Override
	public Optional<Jobs> findByCreatedByAndHrId(Users createdBy, long jobId) 
	{
		return Optional.ofNullable(jobsRepository.findByCreatedByAndJobId(createdBy, jobId).orElse(null));
	}

	@Override
	public boolean deleteJobByHR(Users createdBy, long jobId) {
		Optional<Jobs> optional = findByCreatedByAndHrId(createdBy, jobId);
		if (optional.isPresent()) 
		{
			jobsRepository.deleteById(optional.get().getJobId());
			return true;
		} 
		return false;
	}

	@Override
	public boolean updateJob(Users createdBy, Jobs job, long jobId) 
	{
		Optional<Jobs> optional = findByCreatedByAndHrId(createdBy, jobId);
		if (optional.isPresent()) 
		{
			Jobs fetchJobFromDb = optional.get();
			fetchJobFromDb.setTitle(job.getTitle());
			fetchJobFromDb.setDescription(job.getDescription());
			fetchJobFromDb.setDepartment(job.getDepartment());
			fetchJobFromDb.setLocation(job.getLocation());
			fetchJobFromDb.setExperience(job.getExperience());
			fetchJobFromDb.setSalary(job.getSalary());
			fetchJobFromDb.setOpenings(job.getOpenings());
			fetchJobFromDb.setClosingDate(job.getClosingDate());
			fetchJobFromDb.setActive(job.isActive());
			jobsRepository.save(fetchJobFromDb);
			return true;
		} 
		return false;
	}
}
