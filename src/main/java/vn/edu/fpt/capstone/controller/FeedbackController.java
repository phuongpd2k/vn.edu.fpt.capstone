package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.FeedbackDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.dto.request.FeedbackRequest;
import vn.edu.fpt.capstone.dto.request.FeedbackUpdateRequest;
import vn.edu.fpt.capstone.dto.response.FeedbackResponse;
import vn.edu.fpt.capstone.service.FeedbackService;
import vn.edu.fpt.capstone.service.PostService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedbackController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class.getName());

	@Autowired
	FeedbackService feedbackService;
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;

	@GetMapping(value = "/feedback/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (feedbackService.isExist(lId)) {
				FeedbackDto feedbackDto = feedbackService.findById(lId);
				responseObject.setResults(feedbackDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}", feedbackDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Feedback  is not exist");
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

	@GetMapping(value = "/feedback")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<FeedbackDto> feedbackDtos = feedbackService.findAll();
			if (feedbackDtos == null || feedbackDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(feedbackDtos);
			}
			LOGGER.info("getAll: {}", feedbackDtos);
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

	@GetMapping(value = "/feedback/post/{id}")
	public ResponseEntity<ResponseObject> getByPostId(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			List<FeedbackDto> feedbackDtos = feedbackService.findByPostId(lId);
			FeedbackResponse feedbackResponse = new FeedbackResponse();
			if (feedbackDtos == null || feedbackDtos.isEmpty()) {
				feedbackResponse.setFeedbackDtos(new ArrayList<>());
			} else {
				feedbackResponse.setFeedbackDtos(feedbackDtos);
				feedbackResponse.setCountFeedback(feedbackDtos.size());
				float rating = 0;
				for (FeedbackDto feedbackDto : feedbackDtos) {
					rating += feedbackDto.getRating();
				}
				feedbackResponse.setAverageRating(rating / feedbackDtos.size());
			}
			responseObject.setResults(feedbackResponse);
			LOGGER.info("getByPostId: {}", feedbackDtos);
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (NumberFormatException e) {
			LOGGER.error("getByPostId: {}", e);
			responseObject.setCode("404");
			responseObject.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getByPostId: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER') ")
	@PostMapping(value = "/feedback")
	public ResponseEntity<ResponseObject> postFeedback(@RequestBody FeedbackRequest feedbackRequest,
			@RequestHeader(value = "Authorization") String jwtToken) {
		ResponseObject response = new ResponseObject();
		try {

			UserDto userDto = userService.getUserByToken(jwtToken);
			if (userDto == null) {
				response.setCode("406");
				response.setMessage("Wrong body format or User token has expired");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Wrong body format or User token has expired");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			if (feedbackRequest.getContent() == null) {
				response.setCode("406");
				response.setMessage("Content can't null");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Content can't null");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			if (feedbackRequest.getPostId() == null || !postService.isExist(feedbackRequest.getPostId())) {
				response.setCode("406");
				response.setMessage("Post Id isn't exist");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Post Id isn't exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			List<FeedbackDto> feedbacks = feedbackService.findByPostIdAndUserId(feedbackRequest.getPostId(),
					userDto.getId());
			if (!(feedbacks == null || feedbacks.isEmpty())) {
				response.setCode("406");
				response.setMessage("You can only give feedback once");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Can't feedback more than 1");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			FeedbackDto feedbackDto = feedbackService.createFeedback(new FeedbackDto(userDto.getId(),
					feedbackRequest.getContent(), feedbackRequest.getRating(), feedbackRequest.getPostId()));
			if (feedbackDto == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(feedbackDto);
			LOGGER.info("postFeedback: {}", feedbackDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postFeedback: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER') ")
	@PutMapping(value = "/feedback")
	public ResponseEntity<ResponseObject> putFeedback(@RequestBody FeedbackUpdateRequest feedbackUpdateRequest,
			@RequestHeader(value = "Authorization") String jwtToken) {
		ResponseObject response = new ResponseObject();
		try {
			UserDto userDto = userService.getUserByToken(jwtToken);
			if (userDto == null) {
				response.setCode("406");
				response.setMessage("Wrong body format or User token has expired");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Wrong body format or User token has expired");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			if (feedbackUpdateRequest.getId() == null || !feedbackService.isExist(feedbackUpdateRequest.getId())) {
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				LOGGER.error("putFeedback: {}", "ID Feedback  is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (feedbackUpdateRequest.getContent() == null) {
				response.setCode("406");
				response.setMessage("Content can't null");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Content can't null");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			FeedbackDto feedbackDto = feedbackService.findById(feedbackUpdateRequest.getId());
			if (userDto.getId() != feedbackDto.getUserId()) {
				response.setCode("406");
				response.setMessage("You can't update this feedback");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Cant change feedback of another user");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			feedbackDto.setContent(feedbackUpdateRequest.getContent());
			feedbackDto.setRating(feedbackUpdateRequest.getRating());
			FeedbackDto feedbackDto2 = feedbackService.updateFeedback(feedbackDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(feedbackDto2);
			LOGGER.info("putFeedback: {}", feedbackDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putFeedback: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER') ")
	@DeleteMapping(value = "/feedback/{id}")
	public ResponseEntity<ResponseObject> deleteFeedback(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !feedbackService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteFeedback: {}", "ID Feedback  is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			feedbackService.removeFeedback(Long.valueOf(id));
			LOGGER.error("deleteFeedback: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteFeedback: {}", ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteFeedback: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
