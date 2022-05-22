package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomTypeDto;
import vn.edu.fpt.capstone.common.Message;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.RoomTypeService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomTypeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomTypeController.class.getName());

	@Autowired
	RoomTypeService roomTypeService;

	@GetMapping(value = "/roomType/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (roomTypeService.isExist(lId)) {
				RoomTypeDto roomTypeDto = roomTypeService.findById(lId);
				responseObject.setResults(roomTypeDto);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				LOGGER.error("getById: {}",roomTypeDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}","ID Room Type is not exist");
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

	@GetMapping(value = "/roomType")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<RoomTypeDto> roomTypeDtos = roomTypeService.findAll();
			if (roomTypeDtos == null || roomTypeDtos.isEmpty()) {
				LOGGER.error("getAll: {}","Data is empty");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(roomTypeDtos);
			responseObject.setCode("200");
			responseObject.setMessage(Message.OK);
			LOGGER.info("getAll: {}",roomTypeDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/roomType")
	public ResponseEntity<ResponseObject> postRoomType(@RequestBody RoomTypeDto roomTypeDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (roomTypeDto.getId() != null) {
				LOGGER.error("postRoomType: {}","Wrong body format");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			RoomTypeDto roomTypeDto2 = roomTypeService.createRoomType(roomTypeDto);
			if (roomTypeDto2 == null) {
				response.setCode("500");
				response.setMessage(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(roomTypeDto2);
			LOGGER.info("postRoomType: {}",roomTypeDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postRoomType: {}",e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/roomType")
	public ResponseEntity<ResponseObject> putRoomType(@RequestBody RoomTypeDto roomTypeDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (roomTypeDto.getId() == null || !roomTypeService.isExist(roomTypeDto.getId())) {
				LOGGER.error("putRoomType: {}","ID Room Type is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			RoomTypeDto roomTypeDto2 = roomTypeService.updateRoomType(roomTypeDto);
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(roomTypeDto2);
			LOGGER.info("putRoomType: {}",roomTypeDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putRoomType: {}",e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/roomType/{id}")
	public ResponseEntity<ResponseObject> deleteRoomType(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !roomTypeService.isExist(Long.valueOf(id))) {
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			roomTypeService.removeRoomType(Long.valueOf(id));
			LOGGER.error("deleteRoomType: {}","DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteRoomType: {}",ex);
			response.setCode("404");
			response.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteRoomType: {}",e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
