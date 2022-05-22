package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomRentalHistoryDto;
import vn.edu.fpt.capstone.common.Message;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.RoomRentalHistoryService;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomRentalHistoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomRentalHistoryController.class.getName());

	@Autowired
	RoomRentalHistoryService roomRentalHistoryService;
	@Autowired
	RoomService roomService;

	@GetMapping(value = "/roomRentalHistory/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (roomRentalHistoryService.isExist(lId)) {
				RoomRentalHistoryDto roomRentalHistoryDto = roomRentalHistoryService.findById(lId);
				responseObject.setResults(roomRentalHistoryDto);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				LOGGER.info("getById: {}", roomRentalHistoryDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Room Rental History is not exist");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}", e);
			responseObject.setCode("404");
			responseObject.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/roomRentalHistory")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<RoomRentalHistoryDto> roomRentalHistoryDtos = roomRentalHistoryService.findAll();
			if (roomRentalHistoryDtos == null || roomRentalHistoryDtos.isEmpty()) {
				LOGGER.error("getAll: {}", "Data is empty");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(roomRentalHistoryDtos);
			responseObject.setCode("200");
			responseObject.setMessage(Message.OK);
			LOGGER.error("getAll: {}", roomRentalHistoryDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/roomRentalHistory")
	public ResponseEntity<ResponseObject> postRoomRentalHistory(
			@RequestBody RoomRentalHistoryDto roomRentalHistoryDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (roomRentalHistoryDto.getId() != null || (roomRentalHistoryDto.getRoomId() == null
					|| !roomService.isExist(roomRentalHistoryDto.getRoomId()))) {
				LOGGER.error("postRoomRentalHistory: {}", "Wrong body format or ID Room is not exist");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			response.setMessage(Message.OK);
			RoomRentalHistoryDto roomRentalHistoryDto2 = roomRentalHistoryService
					.createRoomRentalHistory(roomRentalHistoryDto);
			if (roomRentalHistoryDto2 == null) {
				response.setCode("500");
				response.setMessage(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(roomRentalHistoryDto2);
			LOGGER.info("postRoomRentalHistory: {}", roomRentalHistoryDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postRoomRentalHistory: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/roomRentalHistory")
	public ResponseEntity<ResponseObject> putRoomRentalHistory(@RequestBody RoomRentalHistoryDto roomRentalHistoryDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (roomRentalHistoryDto.getId() == null
					|| !roomRentalHistoryService.isExist(roomRentalHistoryDto.getId())) {
				LOGGER.error("putRoomRentalHistory: {}", "ID Room Rental History is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (roomRentalHistoryDto.getRoomId() == null || !roomService.isExist(roomRentalHistoryDto.getRoomId())) {
				LOGGER.error("putRoomRentalHistory: {}", "ID Room is not exist");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			RoomRentalHistoryDto roomRentalHistoryDto2 = roomRentalHistoryService
					.updateRoomRentalHistory(roomRentalHistoryDto);
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(roomRentalHistoryDto2);
			LOGGER.info("putRoomRentalHistory: {}", roomRentalHistoryDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putRoomRentalHistory: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/roomRentalHistory/{id}")
	public ResponseEntity<ResponseObject> deleteRoomRentalHistory(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !roomRentalHistoryService.isExist(Long.valueOf(id))) {
				LOGGER.info("deleteRoomRentalHistory: {}", "ID Room Rental History is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			roomRentalHistoryService.removeRoomRentalHistory(Long.valueOf(id));
			LOGGER.info("deleteRoomRentalHistory: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteRoomRentalHistory: {}", ex);
			response.setCode("404");
			response.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteRoomRentalHistory: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
