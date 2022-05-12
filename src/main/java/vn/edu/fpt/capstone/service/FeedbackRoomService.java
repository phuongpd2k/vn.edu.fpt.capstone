package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.FeedbackRoomDto;

public interface FeedbackRoomService {
    FeedbackRoomDto findById(Long id);
    List<FeedbackRoomDto> findAll();
    FeedbackRoomDto updateFeedbackRoom(FeedbackRoomDto feedbackRoomDto);
    boolean removeFeedbackRoom(Long id);
    FeedbackRoomDto createFeedbackRoom(FeedbackRoomDto feedbackRoomDto);
    boolean isExist(Long id);

}
