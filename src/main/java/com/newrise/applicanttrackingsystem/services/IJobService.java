package com.newrise.applicanttrackingsystem.services;

import com.newrise.applicanttrackingsystem.entities.Jobs;

public interface IJobService 
{
	public Jobs createPost(long userid, Jobs job);
}
