package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.FeedbackDto;

public interface FeedbackService {
	FeedbackDto findById(Long id);

	List<FeedbackDto> findAll();

	List<FeedbackDto> findByPostId(Long postId);

	List<FeedbackDto> findByPostIdAndUserId(Long postId, Long userId);

	FeedbackDto updateFeedback(FeedbackDto feedbackDto);

	boolean removeFeedback(Long id);

	FeedbackDto createFeedback(FeedbackDto feedbackDto);

	boolean isExist(Long id);

}
