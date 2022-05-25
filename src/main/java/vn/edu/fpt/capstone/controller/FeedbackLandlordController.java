package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.FeedbackLandlordDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.FeedbackLandlordService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedbackLandlordController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackLandlordController.class.getName());

	@Autowired
	FeedbackLandlordService feedbackLandlordService;
	@Autowired
	UserService userService;

	@GetMapping(value = "/feedbackLandlord/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (feedbackLandlordService.isExist(lId)) {
				FeedbackLandlordDto feedbackLandlordDto = feedbackLandlordService.findById(lId);
				responseObject.setResults(feedbackLandlordDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}", feedbackLandlordDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Feedback Landlord is not exist");
				responseObject.setCode("404");
				responseObject.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}", e);
			responseObject.setCode("404");
			responseObject.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/feedbackLandlord")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<FeedbackLandlordDto> feedbackLandlordDtos = feedbackLandlordService.findAll();
			if (feedbackLandlordDtos == null || feedbackLandlordDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(feedbackLandlordDtos);
			}
			LOGGER.info("getAll: {}", feedbackLandlordDtos);
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/feedbackLandlord")
	public ResponseEntity<ResponseObject> postFeedbackLandlord(@RequestBody FeedbackLandlordDto feedbackLandlordDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (feedbackLandlordDto.getId() != null || (feedbackLandlordDto.getUserId() == null
					|| userService.checkIdExist(feedbackLandlordDto.getUserId()))) {
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postFeedbackLandlord: {}", "Wrong body format or ID User is not exist");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			FeedbackLandlordDto feedbackLandlordDto2 = feedbackLandlordService
					.createFeedbackLandlord(feedbackLandlordDto);
			if (feedbackLandlordDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(feedbackLandlordDto2);
			LOGGER.info("postFeedbackLandlord: {}", feedbackLandlordDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postFeedbackLandlord: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/feedbackLandlord")
	public ResponseEntity<ResponseObject> putFeedbackLandlord(@RequestBody FeedbackLandlordDto feedbackLandlordDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (feedbackLandlordDto.getId() == null || !feedbackLandlordService.isExist(feedbackLandlordDto.getId())) {
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				LOGGER.error("putFeedbackLandlord: {}", "ID Feedback Landlord is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (feedbackLandlordDto.getUserId() == null || userService.checkIdExist(feedbackLandlordDto.getUserId())) {
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("putFeedbackLandlord: {}", "ID User is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			FeedbackLandlordDto feedbackLandlordDto2 = feedbackLandlordService
					.updateFeedbackLandlord(feedbackLandlordDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(feedbackLandlordDto2);
			LOGGER.info("putFeedbackLandlord: {}", feedbackLandlordDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putFeedbackLandlord: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/feedbackLandlord/{id}")
	public ResponseEntity<ResponseObject> deleteFeedbackLandlord(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !feedbackLandlordService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteFeedbackLandlord: {}", "ID Feedback Landlord is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			feedbackLandlordService.removeFeedbackLandlord(Long.valueOf(id));
			LOGGER.error("deleteFeedbackLandlord: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteFeedbackLandlord: {}", ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteFeedbackLandlord: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
