package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.FeedbackRoomDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.FeedbackRoomService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeedbackRoomController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackRoomController.class.getName());

	@Autowired
	FeedbackRoomService feedbackRoomService;
	@Autowired
	RoomService roomService;
	@Autowired
	UserService userService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/feedbackRoom/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (feedbackRoomService.isExist(lId)) {
				FeedbackRoomDto feedbackRoomDto = feedbackRoomService.findById(lId);
				responseObject.setResults(feedbackRoomDto);
				responseObject.setCode("200");
				responseObject.setMessage("Successfully");
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setCode("404");
				responseObject.setMessage("Not found");
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error(e.toString());
			responseObject.setCode("404");
			responseObject.setMessage("Not found");
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage("Internal Server Error");
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/feedbackRoom")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<FeedbackRoomDto> feedbackRoomDtos = feedbackRoomService.findAll();
			if (feedbackRoomDtos == null || feedbackRoomDtos.isEmpty()) {
				responseObject.setCode("404");
				responseObject.setMessage("No data");
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(feedbackRoomDtos);
			responseObject.setCode("200");
			responseObject.setMessage("Successfully");
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage("Internal Server Error");
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/feedbackRoom")
	public ResponseEntity<ResponseObject> postFeedbackRoom(@RequestBody FeedbackRoomDto feedbackRoomDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (feedbackRoomDto.getId() != null
					|| (feedbackRoomDto.getUserId() == null || !userService.checkIdExist(feedbackRoomDto.getUserId()))
					|| (feedbackRoomDto.getRoomId() == null || !roomService.isExist(feedbackRoomDto.getRoomId()))) {
				response.setCode("404");
				response.setMessage("Invalid data");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage("Create successfully");
			feedbackRoomService.createFeedbackRoom(feedbackRoomDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = "/feedbackRoom")
	public ResponseEntity<ResponseObject> putFeedbackRoom(@RequestBody FeedbackRoomDto feedbackRoomDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (feedbackRoomDto.getId() == null || !feedbackRoomService.isExist(feedbackRoomDto.getId())
					|| (feedbackRoomDto.getUserId() == null || !userService.checkIdExist(feedbackRoomDto.getUserId()))
					|| (feedbackRoomDto.getRoomId() == null || !roomService.isExist(feedbackRoomDto.getRoomId()))) {
				response.setCode("404");
				response.setMessage("Invalid data");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage("Update successfully");
			feedbackRoomService.updateFeedbackRoom(feedbackRoomDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/feedbackRoom/{id}")
	public ResponseEntity<ResponseObject> deleteFeedbackRoom(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !feedbackRoomService.isExist(Long.valueOf(id))) {
				response.setCode("404");
				response.setMessage("Id is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage("Delete successfully");
			feedbackRoomService.removeFeedbackRoom(Long.valueOf(id));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error(ex.toString());
			response.setCode("404");
			response.setMessage("Id is not exist");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("1001");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
