package com.newrise.applicanttrackingsystem.servicesimpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Feedback;
import com.newrise.applicanttrackingsystem.entities.Interview;
import com.newrise.applicanttrackingsystem.entities.JobApplications;
import com.newrise.applicanttrackingsystem.repository.ApplicationsRepository;
import com.newrise.applicanttrackingsystem.repository.FeedbacksRepository;
import com.newrise.applicanttrackingsystem.services.IFeedbackServices;

@Service
public class FeedbackServiceImpl implements IFeedbackServices {
	@Autowired
	private FeedbacksRepository feedbacksRepository;

	@Override
	public boolean createFeedback(Feedback feedback) {
		try {
			feedbacksRepository.save(feedback);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Optional<Feedback> getFeedback(Long feedbackId) {
		try {
			return feedbacksRepository.findById(feedbackId);
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public Feedback updateFeedback(Feedback feedback) {
		try {
			return feedbacksRepository.save(feedback);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Feedback> getFeedbacksOnAnInterview(Interview interview) {
		try {
			return feedbacksRepository.findByInterview(interview);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteFeedback(Feedback feedback) {
		try {
			feedbacksRepository.deleteById(feedback.getFeedbackId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Feedback> getAllFeedbacksOnAnApplication(JobApplications application) {
		return application != null && application.getInterviews() != null
				? application.getInterviews().stream().filter(Objects::nonNull)
						.flatMap(interview -> Optional.ofNullable(interview.getFeedbackList())
								.orElse(Collections.emptySet()).stream())
						.toList()
				: Collections.emptyList();
	}

	@Override
	public List<Feedback> getAllFeedbacksByRatings(int rating) {
		try {
			return feedbacksRepository.findByRating(rating);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
