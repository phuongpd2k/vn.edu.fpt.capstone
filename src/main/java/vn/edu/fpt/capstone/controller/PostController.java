package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.response.PostResponse;
import vn.edu.fpt.capstone.service.HouseService;
import vn.edu.fpt.capstone.service.PostService;
import vn.edu.fpt.capstone.service.PostTypeService;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class.getName());

	@Autowired
	PostService postService;

	@Autowired
	RoomService roomService;

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private PostTypeService postTypeService;

	@Autowired
	ObjectMapper objectMapper;

	public static int TIMESTAMP_DAY = 86400000;

	@GetMapping(value = "/post/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (postService.isExist(lId)) {
				PostDto postDto = postService.findById(lId);
				responseObject.setResults(postDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}", postDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Post is not exist");
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

	@GetMapping(value = "/post")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<PostResponse> postDtos = postService.findAll();
			if (postDtos == null || postDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(postDtos);
			}
			LOGGER.info("getAll: {}", postDtos);
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

//	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER')")
//	@PostMapping(value = "/post")
//	public ResponseEntity<ResponseObject> postPost(@RequestBody String jsonString) {
//		ResponseObject response = new ResponseObject();
//		try {
//			PostDto postDto = objectMapper.readValue(jsonString, PostDto.class);
//			LOGGER.info("postHouseCreate: {}", postDto);
//			if (postDto.getId() != null) {
//				LOGGER.error("postPost: {}", "Wrong body format");
//				response.setCode("406");
//				response.setMessage("Wrong body format");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//
//			if (postDto.getNumberOfDays() == null || postDto.getNumberOfDays() <= 0) {
//				LOGGER.error("postPost: {}", "Number Of Days Public Post must greater than 0");
//				response.setCode("406");
//				response.setMessage("Number Of Days Public Post must greater than 0");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//
//			if (postDto.getTitle() == null || postDto.getTitle().trim().isEmpty()) {
//				LOGGER.error("postPost: {}", "Title must not empty");
//				response.setCode("406");
//				response.setMessage("Title must not empty");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//			if (postDto.getDescription() == null || postDto.getDescription().trim().isEmpty()) {
//				LOGGER.error("postPost: {}", "Description must not empty");
//				response.setCode("406");
//				response.setMessage("Description must not empty");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//			if (postDto.getRoom() == null || postDto.getRoom().getId() == null
//					|| !roomService.isExist(postDto.getRoom().getId())) {
//				LOGGER.error("postPost: {}", "ID Room is not exist");
//				response.setCode("406");
//				response.setMessage("ID Room is not exist");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//			Long expiredTime = System.currentTimeMillis() + (postDto.getNumberOfDays() * TIMESTAMP_DAY);
//			postDto.setExpiredTime(expiredTime);
//			PostDto postDto2 = postService.createPost(postDto);
//			if (postDto2 == null) {
//				response.setCode("500");
//				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//			response.setCode("200");
//			response.setMessageCode(Message.OK);
//			response.setResults(postDto2);
//			LOGGER.info("postPost: {}", postDto2);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} catch (Exception e) {
//			LOGGER.error("postPost: {}", e);
//			response.setCode("500");
//			response.setMessage(e.toString());
//			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER')")
	@PostMapping(value = "/post")
	// DungTV29
	public ResponseEntity<ResponseObject> postCreatePost(@RequestBody PostDto postDto) {
		try {
			// PostDto postDto = objectMapper.readValue(jsonString, PostDto.class);
			LOGGER.info("postHouseCreate: {}", postDto);
			if (postDto.getHouse().getId() == null || !houseService.isExist(postDto.getHouse().getId())) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: id house null or not exits").messageCode("CREATE_POST_FAILED").build());
			}

			if (postDto.getRoom().getId() == null || !roomService.isExist(postDto.getRoom().getId())) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: id room null or not exits").messageCode("CREATE_POST_FAILED").build());
			}

			if (postDto.getPostType().getId() == null || !postTypeService.isExist(postDto.getPostType().getId())) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: id post type null").messageCode("CREATE_POST_FAILED").build());
			}
			
			if (postDto.getStartDate() == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: start date null").messageCode("CREATE_POST_FAILED").build());
			}

			if (postDto.getNumberOfDays() <= 0) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: number of day < 0").messageCode("CREATE_POST_FAILED").build());
			}
			
			postService.createPost(postDto);
			
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Create post: successfully").messageCode("CREATE_POST_SUCCESSFULLY").build());
		} catch (Exception e) {
			LOGGER.error("postPost: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Create post: " + e.getMessage()).messageCode("CREATE_POST_FAILED").build());
		}
	}

//	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER')")
//	@PutMapping(value = "/post")
//	public ResponseEntity<ResponseObject> putPost(@RequestBody PostDto postDto) {
//		ResponseObject response = new ResponseObject();
//		try {
//			if (postDto.getId() == null || !postService.isExist(postDto.getId())) {
//				LOGGER.error("putPost: {}", "ID Post is not exist");
//				response.setCode("404");
//				response.setMessageCode(Message.NOT_FOUND);
//				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//			}
//			PostDto postTemp = postService.findById(postDto.getId());
//			if (postDto.getNumberOfDays() == null || postDto.getNumberOfDays() <= 0) {
//				LOGGER.error("postPost: {}", "Number Of Days Public Post must greater than 0");
//				response.setCode("406");
//				response.setMessage("Number Of Days Public Post must greater than 0");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//
//			if (postDto.getTitle() == null || postDto.getTitle().trim().isEmpty()) {
//				LOGGER.error("postPost: {}", "Title must not empty");
//				response.setCode("406");
//				response.setMessage("Title must not empty");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//			if (postDto.getDescription() == null || postDto.getDescription().trim().isEmpty()) {
//				LOGGER.error("postPost: {}", "Description must not empty");
//				response.setCode("406");
//				response.setMessage("Description must not empty");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//			if (postDto.getRoom() == null || postDto.getRoom().getId() == null
//					|| !roomService.isExist(postDto.getRoom().getId())) {
//				LOGGER.error("postPost: {}", "ID Room is not exist");
//				response.setCode("406");
//				response.setMessage("ID Room is not exist");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//			Long expiredTime = postTemp.getExpiredTime() + (postDto.getNumberOfDays() * TIMESTAMP_DAY);
//			postDto.setExpiredTime(expiredTime);
//			PostDto postDto2 = postService.updatePost(postDto);
//			response.setCode("200");
//			response.setMessageCode(Message.OK);
//			response.setResults(postDto2);
//			LOGGER.info("putPost: {}", postDto2);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} catch (Exception e) {
//			LOGGER.error("putPost: {}", e);
//			response.setCode("500");
//			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER')")
	@DeleteMapping(value = "/post/{id}")
	public ResponseEntity<ResponseObject> deletePost(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !postService.isExist(Long.valueOf(id))) {
				LOGGER.error("deletePost: {}", "ID Post is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			postService.removePost(Long.valueOf(id));
			LOGGER.error("deletePost: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deletePost: {}", ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deletePost: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
