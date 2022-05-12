package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.FeedbackLandlordDto;

public interface FeedbackLandlordService {
    FeedbackLandlordDto findById(Long id);
    List<FeedbackLandlordDto> findAll();
    FeedbackLandlordDto updateFeedbackLandlord(FeedbackLandlordDto feedbackLandlordDto);
    boolean removeFeedbackLandlord(Long id);
    FeedbackLandlordDto createFeedbackLandlord(FeedbackLandlordDto feedbackLandlordDto);
    boolean isExist(Long id);

}
