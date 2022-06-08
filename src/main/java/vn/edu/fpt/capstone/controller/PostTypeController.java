package vn.edu.fpt.capstone.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.PostTypeDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.model.PostTypeModel;
import vn.edu.fpt.capstone.service.PostTypeService;

@RestController
@RequestMapping("/api/post-type")
@CrossOrigin(origins = "*", maxAge = 3600)
//DungTV
public class PostTypeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostTypeController.class.getName());
	
	@Autowired
	private PostTypeService postTypeService;
	
	@GetMapping()
	public ResponseEntity<?> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<PostTypeDto> list = postTypeService.findAll();
			LOGGER.info("getAll: {}",list);
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			responseObject.setResults(list);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping()
	public ResponseEntity<?> postPostType(@RequestBody PostTypeDto postTypeDto) {
		try {
			if(postTypeDto.getPrice() < 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400").message("Create post type: price < 0!")
								.messageCode("CREATE_POST_TYPE_FAIL").build());
			}
			
			if(postTypeDto.getType().trim().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400").message("Create post type: type null")
								.messageCode("CREATE_POST_TYPE_FAIL").build());
			}
			
			PostTypeModel postTypeModel = postTypeService.create(postTypeDto);

			if (postTypeModel != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(ResponseObject.builder().code("200").message("Create post type: successfully!")
								.messageCode("CREATE_POST_TYPE_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("postPostType: {}",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseObject.builder().code("500").message("Create post type: fail!")
							.messageCode("CREATE_POST_TYPE_FAIL").build());
		}
	}
	
	@PutMapping()
	public ResponseEntity<?> putPostType(@RequestBody PostTypeDto postTypeDto) {
		try {
			if(postTypeDto.getId() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400").message("Update post type: id null")
								.messageCode("UPDATE_POST_TYPE_FAIL").build());
			}
			
			if(postTypeDto.getPrice() < 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400").message("Update post type: price < 0!")
								.messageCode("UPDATE_POST_TYPE_FAIL").build());
			}
			
			if(postTypeDto.getType().trim().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400").message("Update post type: type null")
								.messageCode("UPDATE_POST_TYPE_FAIL").build());
			}
			
			PostTypeModel postTypeModel = postTypeService.create(postTypeDto);

			if (postTypeModel != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(ResponseObject.builder().code("200").message("Update post type: successfully!")
								.messageCode("UPDATE_POST_TYPE_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("putPostType: {}",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseObject.builder().code("500").message("Update post type: fail!")
							.messageCode("UPDATE_POST_TYPE_FAIL").build());
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deletePostType(@RequestBody ListIdDto listIdDto) {
		try {
			postTypeService.removePostType(listIdDto);
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Delete list post type successfully!")
							.messageCode("DELETE_POST_TYPE_SUCCESSFULL").build());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("Delete list post type fail!")
							.messageCode("DELETE_POST_TYPE_FAIL").build());
		}
	}
}
