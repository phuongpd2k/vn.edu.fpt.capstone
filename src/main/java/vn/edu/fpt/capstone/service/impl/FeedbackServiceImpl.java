package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.FeedbackDto;
import vn.edu.fpt.capstone.model.FeedbackModel;
import vn.edu.fpt.capstone.repository.FeedbackRepository;
import vn.edu.fpt.capstone.service.FeedbackService;

import java.util.Arrays;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class.getName());

	@Autowired
	FeedbackRepository feedbackRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public FeedbackDto findById(Long id) {
		FeedbackDto feedbackDto = modelMapper.map(feedbackRepository.findById(id).get(), FeedbackDto.class);
		return feedbackDto;
	}

	@Override
	public List<FeedbackDto> findAll() {
		List<FeedbackModel> feedbackModels = feedbackRepository.findAll();
		if (feedbackModels == null || feedbackModels.isEmpty()) {
			return null;
		}
		List<FeedbackDto> feedbackDtos = Arrays.asList(modelMapper.map(feedbackModels, FeedbackDto[].class));
		return feedbackDtos;
	}

	@Override
	public FeedbackDto updateFeedback(FeedbackDto feedbackDto) {
		FeedbackModel feedbackModel = modelMapper.map(feedbackDto, FeedbackModel.class);
		FeedbackModel saveModel = feedbackRepository.save(feedbackModel);
		return modelMapper.map(saveModel, FeedbackDto.class);
	}

	@Override
	public boolean removeFeedback(Long id) {
		if (feedbackRepository.existsById(id)) {
			feedbackRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
		try {
			FeedbackModel feedbackModel = modelMapper.map(feedbackDto, FeedbackModel.class);
//			if (feedbackModel.getCreatedAt() == null || feedbackModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				feedbackModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				feedbackModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				feedbackModel.setModifiedAt(feedbackModel.getCreatedAt());
//			}
//			if (feedbackModel.getCreatedBy() == null || feedbackModel.getCreatedBy().isEmpty()) {
//				feedbackModel.setCreatedBy("SYSTEM");
//				feedbackModel.setModifiedBy("SYSTEM");
//			} else {
//				feedbackModel.setModifiedBy(feedbackModel.getCreatedBy());
//			}
			FeedbackModel saveModel = feedbackRepository.save(feedbackModel);
			return modelMapper.map(saveModel, FeedbackDto.class);
		} catch (Exception e) {
			LOGGER.error("createFeedback: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (feedbackRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	@Override
	public List<FeedbackDto> findByPostId(Long postId) {
		List<FeedbackModel> feedbackModels = feedbackRepository.findByPostId(postId);
		if (feedbackModels == null || feedbackModels.isEmpty()) {
			return null;
		}
		List<FeedbackDto> feedbackDtos = Arrays.asList(modelMapper.map(feedbackModels, FeedbackDto[].class));
		return feedbackDtos;
	}

	@Override
	public List<FeedbackDto> findByPostIdAndUserId(Long postId, Long userId) {
		List<FeedbackModel> feedbackModels = feedbackRepository.findByPostIdAndUserId(postId, userId);
		if (feedbackModels == null || feedbackModels.isEmpty()) {
			return null;
		}
		List<FeedbackDto> feedbackDtos = Arrays.asList(modelMapper.map(feedbackModels, FeedbackDto[].class));
		return feedbackDtos;
	}
}
