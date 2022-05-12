package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.FeedbackRoomDto;
import vn.edu.fpt.capstone.model.FeedbackRoomModel;
import vn.edu.fpt.capstone.repository.FeedbackRoomRepository;
import vn.edu.fpt.capstone.service.FeedbackRoomService;

import java.util.Arrays;
import java.util.List;

@Service
public class FeedbackRoomServiceImpl implements FeedbackRoomService {

    @Autowired
    FeedbackRoomRepository feedbackRoomRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public FeedbackRoomDto findById(Long id) {
        FeedbackRoomDto feedbackRoomDto = modelMapper.map(feedbackRoomRepository.findById(id).get(), FeedbackRoomDto.class);
        return feedbackRoomDto;
    }

    @Override
    public List<FeedbackRoomDto> findAll() {
        List<FeedbackRoomModel> feedbackRoomModels = feedbackRoomRepository.findAll();
        if (feedbackRoomModels == null || feedbackRoomModels.isEmpty()) {
            return null;
        }
        List<FeedbackRoomDto> feedbackRoomDtos = Arrays.asList(modelMapper.map(feedbackRoomModels, FeedbackRoomDto[].class));
        return feedbackRoomDtos;
    }

    @Override
    public FeedbackRoomDto updateFeedbackRoom(FeedbackRoomDto feedbackRoomDto) {
        FeedbackRoomModel feedbackRoomModel = modelMapper.map(feedbackRoomDto,FeedbackRoomModel.class);
        FeedbackRoomModel saveModel = feedbackRoomRepository.save(feedbackRoomModel);
        return modelMapper.map(saveModel,FeedbackRoomDto.class);
    }

    @Override
    public boolean removeFeedbackRoom(Long id) {
        if(feedbackRoomRepository.existsById(id)){
            feedbackRoomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public FeedbackRoomDto createFeedbackRoom(FeedbackRoomDto feedbackRoomDto) {
        FeedbackRoomModel feedbackRoomModel = modelMapper.map(feedbackRoomDto,FeedbackRoomModel.class);
        FeedbackRoomModel saveModel = feedbackRoomRepository.save(feedbackRoomModel);
        return modelMapper.map(saveModel,FeedbackRoomDto.class);
    }

    @Override
    public boolean isExist(Long id) {
        if(feedbackRoomRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
