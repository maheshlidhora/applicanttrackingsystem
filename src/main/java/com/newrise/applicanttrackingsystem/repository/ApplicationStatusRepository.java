package com.newrise.applicanttrackingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.ApplicationStatus;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long>
{
	Optional<ApplicationStatus> findByStatusName(String statusName);
}
