package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.FeedbackLandlordDto;
import vn.edu.fpt.capstone.model.FeedbackLandlordModel;
import vn.edu.fpt.capstone.repository.FeedbackLandLordRepository;
import vn.edu.fpt.capstone.service.FeedbackLandlordService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class FeedbackLandlordServiceImpl implements FeedbackLandlordService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackLandlordServiceImpl.class.getName());

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
		FeedbackLandlordModel feedbackLandlordModel = modelMapper.map(feedbackLandlordDto, FeedbackLandlordModel.class);
		FeedbackLandlordModel saveModel = feedbackLandlordRepository.save(feedbackLandlordModel);
		return modelMapper.map(saveModel, FeedbackLandlordDto.class);
	}

	@Override
	public boolean removeFeedbackLandlord(Long id) {
		if (feedbackLandlordRepository.existsById(id)) {
			feedbackLandlordRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public FeedbackLandlordDto createFeedbackLandlord(FeedbackLandlordDto feedbackLandlordDto) {
		try {
			FeedbackLandlordModel feedbackLandlordModel = modelMapper.map(feedbackLandlordDto, FeedbackLandlordModel.class);
//			if (feedbackLandlordModel.getCreatedAt() == null || feedbackLandlordModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				feedbackLandlordModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				feedbackLandlordModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				feedbackLandlordModel.setModifiedAt(feedbackLandlordModel.getCreatedAt());
//			}
//			if (feedbackLandlordModel.getCreatedBy() == null || feedbackLandlordModel.getCreatedBy().isEmpty()) {
//				feedbackLandlordModel.setCreatedBy("SYSTEM");
//				feedbackLandlordModel.setModifiedBy("SYSTEM");
//			} else {
//				feedbackLandlordModel.setModifiedBy(feedbackLandlordModel.getCreatedBy());
//			}
			FeedbackLandlordModel saveModel = feedbackLandlordRepository.save(feedbackLandlordModel);
			return modelMapper.map(saveModel, FeedbackLandlordDto.class);
		} catch (Exception e) {
			LOGGER.error("createFeedbackLandlord: {}", e);
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
        if(feedbackLandlordRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
