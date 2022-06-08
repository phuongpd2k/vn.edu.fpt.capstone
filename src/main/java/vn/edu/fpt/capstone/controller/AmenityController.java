package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.AmenityService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AmenityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AmenityController.class.getName());

	@Autowired
	AmenityService amenityService;

	@GetMapping(value = "/amenity/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (amenityService.isExist(lId)) {
				AmenityDto amenityDto = amenityService.findById(lId);
				responseObject.setResults(amenityDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}",amenityDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}","ID Amenity is not exist");
				responseObject.setCode("404");
				responseObject.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}",e);
			responseObject.setCode("404");
			responseObject.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/amenity")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<AmenityDto> amenityDtos = amenityService.findAll();
			if (amenityDtos == null || amenityDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(amenityDtos);
			}
			LOGGER.info("getAll: {}",amenityDtos);
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/amenity")
	public ResponseEntity<ResponseObject> postAmenity(@RequestBody AmenityDto amenityDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (amenityDto.getId() != null) {
				LOGGER.error("postAmenity: {}","Wrong body format");
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			AmenityDto amenityDto2 = amenityService.createAmenity(amenityDto);
			if (amenityDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(amenityDto2);
			LOGGER.info("postAmenity: {}",amenityDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postAmenity: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/amenity")
	public ResponseEntity<ResponseObject> putAmenity(@RequestBody AmenityDto amenityDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (amenityDto.getId() == null || !amenityService.isExist(amenityDto.getId())) {
				LOGGER.error("putAmenity: {}","ID Amenity is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			AmenityDto amenityDto2 = amenityService.updateAmenity(amenityDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(amenityDto2);
			LOGGER.info("putAmenity: {}",amenityDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putAmenity: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/amenity/{id}")
	public ResponseEntity<ResponseObject> deleteAmenity(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !amenityService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteAmenity: {}","ID Amenity is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			amenityService.removeAmenity(Long.valueOf(id));
			LOGGER.error("deleteAmenity: {}","DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteAmenity: {}",ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteAmenity: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/amenity")
	public ResponseEntity<?> deleteListAmenity(@RequestBody ListIdDto listIdDto) {
		try {
			amenityService.removeListAmenity(listIdDto);
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
