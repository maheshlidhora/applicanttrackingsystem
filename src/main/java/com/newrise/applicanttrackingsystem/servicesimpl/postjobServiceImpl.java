package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.PostJob;
import com.newrise.applicanttrackingsystem.repository.postjobRepository;
import com.newrise.applicanttrackingsystem.services.PostjobService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class postjobServiceImpl implements PostjobService {

	@Autowired
	private postjobRepository postjobRepository;

	@Override
	public PostJob createPost(PostJob postJob) {
		try {
			log.info("post insert seccussefully ");
			return postjobRepository.save(postJob);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Optional<PostJob> getById(int id) {

		try {
			return postjobRepository.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public PostJob updatepostjob(PostJob postJob, int id) {

		try {
			PostJob updatepost = postjobRepository.findById(id).get();
			updatepost.setTitle(postJob.getTitle());
			updatepost.setDepartment(postJob.getDepartment());
			updatepost.setLocation(postJob.getLocation());
			updatepost.setExperienceRequired(postJob.getExperienceRequired());
			updatepost.setSalary(postJob.getSalary());
			updatepost.setSkill(postJob.getSkill());
			updatepost.setDescription(postJob.getDescription());
			updatepost.setLocalDateTime(postJob.getLocalDateTime());
			return postjobRepository.save(updatepost);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;

	}

	@Override
	public List<PostJob> getAllpost(int pageNumber, int pagesize, String sortDir, String sortfield) {
    Sort sortby = sortDir.equalsIgnoreCase("asc")?
    		Sort.by(sortfield).ascending():Sort.by(sortfield).descending();		
		
		
		try {

			Pageable p = PageRequest.of(pageNumber, pagesize, sortby);
			Page<PostJob> pagepost = postjobRepository.findAll(p);

			List<PostJob> pages = pagepost.getContent();
			return pages;
//			return (List<PostJob>) postjobRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deletePostjob(int id) {
		try {
			PostJob postJob = postjobRepository.findById(id).get();
			postjobRepository.delete(postJob);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
