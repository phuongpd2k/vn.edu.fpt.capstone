package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.FeedbackLandlordDto;
import vn.edu.fpt.capstone.model.FeedbackLandlordModel;
import vn.edu.fpt.capstone.repository.FeedbackLandLordRepository;
import vn.edu.fpt.capstone.service.FeedbackLandlordService;

import java.util.Arrays;
import java.util.List;

@Service
public class FeedbackLandlordServiceImpl implements FeedbackLandlordService {

    @Autowired
    FeedbackLandLordRepository feedbackLandlordRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public FeedbackLandlordDto findById(Long id) {
        FeedbackLandlordDto feedbackLandlordDto = modelMapper.map(feedbackLandlordRepository.findById(id).get(), FeedbackLandlordDto.class);
        return feedbackLandlordDto;
    }

    @Override
    public List<FeedbackLandlordDto> findAll() {
        List<FeedbackLandlordModel> feedbackLandlordModels = feedbackLandlordRepository.findAll();
        if (feedbackLandlordModels == null || feedbackLandlordModels.isEmpty()) {
            return null;
        }
        List<FeedbackLandlordDto> feedbackLandlordDtos = Arrays.asList(modelMapper.map(feedbackLandlordModels, FeedbackLandlordDto[].class));
        return feedbackLandlordDtos;
    }

    @Override
    public FeedbackLandlordDto updateFeedbackLandlord(FeedbackLandlordDto feedbackLandlordDto) {
        FeedbackLandlordModel feedbackLandlordModel = modelMapper.map(feedbackLandlordDto,FeedbackLandlordModel.class);
        FeedbackLandlordModel saveModel = feedbackLandlordRepository.save(feedbackLandlordModel);
        return modelMapper.map(saveModel,FeedbackLandlordDto.class);
    }

    @Override
    public boolean removeFeedbackLandlord(Long id) {
        if(feedbackLandlordRepository.existsById(id)){
            feedbackLandlordRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public FeedbackLandlordDto createFeedbackLandlord(FeedbackLandlordDto feedbackLandlordDto) {
        FeedbackLandlordModel feedbackLandlordModel = modelMapper.map(feedbackLandlordDto,FeedbackLandlordModel.class);
        FeedbackLandlordModel saveModel = feedbackLandlordRepository.save(feedbackLandlordModel);
        return modelMapper.map(saveModel,FeedbackLandlordDto.class);
    }

    @Override
    public boolean isExist(Long id) {
        if(feedbackLandlordRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
