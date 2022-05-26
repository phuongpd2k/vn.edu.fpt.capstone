package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.dto.RoomImageDto;
import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.HouseService;
import vn.edu.fpt.capstone.service.RoomCategoryService;
import vn.edu.fpt.capstone.service.RoomImageService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.RoomTypeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class.getName());

	@Autowired
	RoomService roomService;
	@Autowired
	RoomCategoryService roomCategoryService;
	@Autowired
	HouseService houseService;
	@Autowired
	RoomTypeService roomTypeService;
	@Autowired
	RoomImageService roomImageService;

	@GetMapping(value = "/room/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (roomService.isExist(lId)) {
				RoomDto roomDto = roomService.findById(lId);
				LOGGER.info("getById: {}", "roomDto");
				responseObject.setResults(roomDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Room is not exist");
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

	@GetMapping(value = "/room")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<RoomDto> roomDtos = roomService.findAll();
			if (roomDtos == null || roomDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(roomDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			LOGGER.info("getAll: {}", roomDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/room")
	public ResponseEntity<?> postRoom(@RequestBody RoomDto roomDto) {
		try {
			return roomService.create(roomDto);
		} catch (Exception e) {
			LOGGER.error("Create room fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room fail: " + e.getMessage()).messageCode("CREATE_ROOM_FAIL").build());
		}
	}
	
	@PutMapping(value = "/room")
	public ResponseEntity<?> putRoom(@RequestBody RoomDto roomDto) {
		try {
			return roomService.update(roomDto);
		} catch (Exception e) {
			LOGGER.error("Update room fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Update room fail 0: " + e.getMessage()).messageCode("UPDATE_ROOM_FAIL").build());
		}
	}

	@DeleteMapping(value = "/room/{id}")
	public ResponseEntity<ResponseObject> deleteRoom(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !roomService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteRoom: {}", "ID Room is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			roomService.removeRoom(Long.valueOf(id));
			LOGGER.info("deleteRoom: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteRoom: {}", ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteRoom: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
