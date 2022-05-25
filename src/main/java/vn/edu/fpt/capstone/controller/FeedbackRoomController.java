package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.FeedbackRoomDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.FeedbackRoomService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedbackRoomController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackRoomController.class.getName());

	@Autowired
	FeedbackRoomService feedbackRoomService;
	@Autowired
	RoomService roomService;
	@Autowired
	UserService userService;

	@GetMapping(value = "/feedbackRoom/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (feedbackRoomService.isExist(lId)) {
				FeedbackRoomDto feedbackRoomDto = feedbackRoomService.findById(lId);
				responseObject.setResults(feedbackRoomDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}", feedbackRoomDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Feedback Room is not exist");
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

	@GetMapping(value = "/feedbackRoom")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<FeedbackRoomDto> feedbackRoomDtos = feedbackRoomService.findAll();
			if (feedbackRoomDtos == null || feedbackRoomDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(feedbackRoomDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			LOGGER.info("getAll: {}", feedbackRoomDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/feedbackRoom")
	public ResponseEntity<ResponseObject> postFeedbackRoom(@RequestBody FeedbackRoomDto feedbackRoomDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (feedbackRoomDto.getId() != null
					|| (feedbackRoomDto.getUserId() == null || !userService.checkIdExist(feedbackRoomDto.getUserId()))
					|| (feedbackRoomDto.getRoomId() == null || !roomService.isExist(feedbackRoomDto.getRoomId()))) {
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postFeedbackRoom: {}", "Wrong body format or ID User is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			FeedbackRoomDto feedbackRoomDto2 = feedbackRoomService.createFeedbackRoom(feedbackRoomDto);
			if (feedbackRoomDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(feedbackRoomDto2);
			LOGGER.info("postFeedbackRoom: {}", feedbackRoomDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postFeedbackRoom: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/feedbackRoom")
	public ResponseEntity<ResponseObject> putFeedbackRoom(@RequestBody FeedbackRoomDto feedbackRoomDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (feedbackRoomDto.getId() == null || !feedbackRoomService.isExist(feedbackRoomDto.getId())) {
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				LOGGER.error("putFeedbackRoom: {}", "ID Feedback Room is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if ((feedbackRoomDto.getUserId() == null || !userService.checkIdExist(feedbackRoomDto.getUserId()))
					|| (feedbackRoomDto.getRoomId() == null || !roomService.isExist(feedbackRoomDto.getRoomId()))) {
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("putFeedbackRoom: {}", "ID User or ID Room is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			FeedbackRoomDto feedbackRoomDto2 = feedbackRoomService.updateFeedbackRoom(feedbackRoomDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(feedbackRoomDto2);
			LOGGER.info("putFeedbackRoom: {}", feedbackRoomDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putFeedbackRoom: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/feedbackRoom/{id}")
	public ResponseEntity<ResponseObject> deleteFeedbackRoom(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !feedbackRoomService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteFeedbackRoom: {}", "ID Feedback Room is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			feedbackRoomService.removeFeedbackRoom(Long.valueOf(id));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error(ex.toString());
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
