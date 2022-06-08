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
import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.PostTypeDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.PostTypeService;

@RestController
@RequestMapping("/api/post-type")
@CrossOrigin(origins = "*", maxAge = 3600)
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
	public ResponseEntity<?> postAmenity(@RequestBody AmenityDto amenityDto) {
		ResponseObject response = new ResponseObject();
		try {
//			if (amenityDto.getId() != null) {
//				LOGGER.error("postAmenity: {}","Wrong body format");
//				response.setCode("406");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//			AmenityDto amenityDto2 = amenityService.createAmenity(amenityDto);
//			if (amenityDto2 == null) {
//				response.setCode("500");
//				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//			response.setCode("200");
//			response.setMessageCode(Message.OK);
//			response.setResults(amenityDto2);
//			LOGGER.info("postAmenity: {}",amenityDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postAmenity: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping()
	public ResponseEntity<ResponseObject> putAmenity(@RequestBody AmenityDto amenityDto) {
		ResponseObject response = new ResponseObject();
		try {
//			if (amenityDto.getId() == null || !amenityService.isExist(amenityDto.getId())) {
//				LOGGER.error("putAmenity: {}","ID Amenity is not exist");
//				response.setCode("404");
//				response.setMessageCode(Message.NOT_FOUND);
//				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//			}
//			AmenityDto amenityDto2 = amenityService.updateAmenity(amenityDto);
//			response.setCode("200");
//			response.setMessageCode(Message.OK);
//			response.setResults(amenityDto2);
//			LOGGER.info("putAmenity: {}",amenityDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putAmenity: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deleteListAmenity(@RequestBody ListIdDto listIdDto) {
		try {
			//amenityService.removeListAmenity(listIdDto);
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Delete list amenity successfully!")
							.messageCode("DELETE_LIST_AMENITY_SUCCESSFULL").build());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("Delete list amenity fail!")
							.messageCode("DELETE_LIST_AMENITY_FAIL").build());
		}
	}
}
