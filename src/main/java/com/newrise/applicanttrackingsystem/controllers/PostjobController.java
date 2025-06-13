package com.newrise.applicanttrackingsystem.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newrise.applicanttrackingsystem.entities.PostJob;
import com.newrise.applicanttrackingsystem.services.PostjobService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class PostjobController {

	@Autowired
	private PostjobService postjobService;

	@PostMapping("/savepost")
	public ResponseEntity<PostJob> insertpostjob(@RequestBody PostJob postJob) {
		PostJob savejob = postjobService.createPost(postJob);
		return new ResponseEntity<PostJob>(savejob, HttpStatus.CREATED);
	}

	@GetMapping("/getpost/{id}")
	public ResponseEntity<Optional<PostJob>> getpostById(@PathVariable int id) {
		Optional<PostJob> getpost = postjobService.getById(id);

		log.info("get successfully post --> " + id);
		return ResponseEntity.ok(getpost);
	}

	@PutMapping("/updatepost/{id}")
	public ResponseEntity<PostJob> updatepostjob(@PathVariable int id, @RequestBody PostJob postJob) {
		PostJob postJob2 = postjobService.updatepostjob(postJob, id);
		log.info("Successfully upadate post");
		return ResponseEntity.ok(postJob2);
	}

	@GetMapping("/getAllpost")
	public ResponseEntity<List<PostJob>> getAllpost
	(@RequestParam(value = "pagesize", defaultValue = "5" , required = false) int pagesize
	, @RequestParam(value = "pageNumber" , defaultValue = "1", required = false) int pageNumber,
	@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir, 
	@RequestParam(value = "sortfield", defaultValue = "id", required = false) String sortfield)
	{
		List<PostJob> list = postjobService.getAllpost(pageNumber, pagesize, sortDir, sortfield);
		log.info("show all post successfully!");
		return new ResponseEntity<List<PostJob>>(list, HttpStatus.OK);
//		return ResponseEntity.ok(list);
	}

	@DeleteMapping("/deletepost/{id}")
	public void deletepost(@PathVariable int id) {
		log.info("seccussefully delete post");
		postjobService.deletePostjob(id);
	}

}
