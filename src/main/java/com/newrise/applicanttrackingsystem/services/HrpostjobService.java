package com.newrise.applicanttrackingsystem.services;

import com.newrise.applicanttrackingsystem.entities.Hrpostjob;

public interface HrpostjobService {
	
	public Hrpostjob inserthr(Hrpostjob hrpostjob);
	
	public Hrpostjob getHrByid(int id);

}
