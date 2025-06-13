package com.newrise.applicanttrackingsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;

import com.newrise.applicanttrackingsystem.entities.PostJob;

public interface PostjobService {

	public PostJob createPost(PostJob postJob);

	public Optional<PostJob> getById(int id);

	public List<PostJob> getAllpost(int pageNumber, int pagesize, String sortDir, String sortfield);

	public PostJob updatepostjob(PostJob postJob, int id);

	public void deletePostjob(int id);

}
