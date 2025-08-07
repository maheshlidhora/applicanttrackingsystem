package com.newrise.applicanttrackingsystem.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.entities.Users;

public interface InterviewsRepository extends JpaRepository<Interview, Long>
{
	boolean existsByInterviewerAndScheduledDateAndScheduledTime(Users interviewer, LocalDate scheduledDate, LocalTime scheduledTime);
}
