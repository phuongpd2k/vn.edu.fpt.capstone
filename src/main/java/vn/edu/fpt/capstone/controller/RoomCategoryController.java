package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomCategoryDto;
import vn.edu.fpt.capstone.common.Message;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.RoomCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomCategoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomCategoryController.class.getName());

	@Autowired
	RoomCategoryService roomCategoryService;

	@GetMapping(value = "/roomCategory/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (roomCategoryService.isExist(lId)) {
				RoomCategoryDto roomCategoryDto = roomCategoryService.findById(lId);
				responseObject.setResults(roomCategoryDto);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				LOGGER.error("getById: {}",roomCategoryDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}","ID Room Category is not exist");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}",e);
			responseObject.setCode("404");
			responseObject.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/roomCategory")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<RoomCategoryDto> roomCategoryDtos = roomCategoryService.findAll();
			if (roomCategoryDtos == null || roomCategoryDtos.isEmpty()) {
				LOGGER.error("getAll: {}","Data is empty");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(roomCategoryDtos);
			responseObject.setCode("200");
			responseObject.setMessage(Message.OK);
			LOGGER.info("getAll: {}",roomCategoryDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/roomCategory")
	public ResponseEntity<ResponseObject> postRoomCategory(@RequestBody RoomCategoryDto roomCategoryDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (roomCategoryDto.getId() != null) {
				LOGGER.error("postRoomCategory: {}","Wrong body format");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			RoomCategoryDto roomCategoryDto2 = roomCategoryService.createRoomCategory(roomCategoryDto);
			if (roomCategoryDto2 == null) {
				response.setCode("500");
				response.setMessage(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(roomCategoryDto2);
			LOGGER.info("postRoomCategory: {}",roomCategoryDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postRoomCategory: {}",e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/roomCategory")
	public ResponseEntity<ResponseObject> putRoomCategory(@RequestBody RoomCategoryDto roomCategoryDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (roomCategoryDto.getId() == null || !roomCategoryService.isExist(roomCategoryDto.getId())) {
				LOGGER.error("putRoomCategory: {}","ID Room Category is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			RoomCategoryDto roomCategoryDto2 = roomCategoryService.updateRoomCategory(roomCategoryDto);
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(roomCategoryDto2);
			LOGGER.info("putRoomCategory: {}",roomCategoryDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putRoomCategory: {}",e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/roomCategory/{id}")
	public ResponseEntity<ResponseObject> deleteRoomCategory(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !roomCategoryService.isExist(Long.valueOf(id))) {
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			roomCategoryService.removeRoomCategory(Long.valueOf(id));
			LOGGER.error("deleteRoomCategory: {}","DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteRoomCategory: {}",ex);
			response.setCode("404");
			response.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteRoomCategory: {}",e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
