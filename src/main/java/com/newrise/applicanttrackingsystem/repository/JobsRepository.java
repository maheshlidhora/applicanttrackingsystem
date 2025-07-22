package com.newrise.applicanttrackingsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Jobs;
import com.newrise.applicanttrackingsystem.entities.Users;
import java.util.List;
import java.util.Optional;


public interface JobsRepository extends JpaRepository<Jobs, Long>
{
	Page<Jobs> findByCreatedBy(Users createdBy, Pageable pageable);
	Optional<Jobs> findByCreatedByAndJobId(Users createdBy, long id);
}
