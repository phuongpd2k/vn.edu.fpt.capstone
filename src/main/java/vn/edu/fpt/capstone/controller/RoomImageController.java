package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomImageDto;
import vn.edu.fpt.capstone.common.Message;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.ImageService;
import vn.edu.fpt.capstone.service.RoomImageService;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomImageController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomImageController.class.getName());

	@Autowired
	RoomImageService roomImageService;
	@Autowired
	RoomService roomService;
	@Autowired
	ImageService imageService;

	@GetMapping(value = "/roomImage/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (roomImageService.isExist(lId)) {
				RoomImageDto roomImageDto = roomImageService.findById(lId);
				responseObject.setResults(roomImageDto);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				LOGGER.info("getById: {}", roomImageDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Room Image is not exist");
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

	@GetMapping(value = "/roomImage")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<RoomImageDto> roomImageDtos = roomImageService.findAll();
			if (roomImageDtos == null || roomImageDtos.isEmpty()) {
				LOGGER.error("getAll: {}", "Data is empty");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(roomImageDtos);
			responseObject.setCode("200");
			responseObject.setMessage(Message.OK);
			LOGGER.error("getAll: {}", roomImageDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/roomImage")
	public ResponseEntity<ResponseObject> postRoomImage(@RequestBody RoomImageDto roomImageDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (roomImageDto.getId() != null
					|| (roomImageDto.getRoomId() == null || !roomService.isExist(roomImageDto.getRoomId()))
					|| (roomImageDto.getImageId() == null || !imageService.isExist(roomImageDto.getImageId()))) {
				LOGGER.error("postRoomImage: {}", "Wrong body format or ID Room or ID Image is not exist");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			RoomImageDto roomImageDto2 = roomImageService.createRoomImage(roomImageDto);
			if (roomImageDto2 == null) {
				response.setCode("500");
				response.setMessage(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(roomImageDto2);
			LOGGER.info("postRoomImage: {}", roomImageDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postRoomImage: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/roomImage")
	public ResponseEntity<ResponseObject> putRoomImage(@RequestBody RoomImageDto roomImageDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (roomImageDto.getId() == null || !roomImageService.isExist(roomImageDto.getId())) {
				LOGGER.error("putRoomImage: {}", "ID Room Image is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if ((roomImageDto.getRoomId() == null || !roomService.isExist(roomImageDto.getRoomId()))
					|| (roomImageDto.getImageId() == null || !imageService.isExist(roomImageDto.getImageId()))) {
				LOGGER.error("putRoomImage: {}", "ID Room or ID Image is not exist");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			RoomImageDto roomImageDto2 = roomImageService.updateRoomImage(roomImageDto);
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(roomImageDto2);
			LOGGER.info("putRoomImage: {}", roomImageDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putRoomImage: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/roomImage/{id}")
	public ResponseEntity<ResponseObject> deleteRoomImage(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !roomImageService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteRoomImage: {}", "ID Room Image is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			roomImageService.removeRoomImage(Long.valueOf(id));
			LOGGER.info("deleteRoomImage: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteRoomImage: {}", ex);
			response.setCode("404");
			response.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteRoomImage: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
