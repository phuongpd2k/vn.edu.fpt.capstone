//package vn.edu.fpt.capstone.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import vn.edu.fpt.capstone.dto.RoomAmenitiesDto;
//import vn.edu.fpt.capstone.constant.Message;
//import vn.edu.fpt.capstone.dto.ResponseObject;
//import vn.edu.fpt.capstone.service.AmenityService;
//import vn.edu.fpt.capstone.service.RoomAmenitiesService;
//import vn.edu.fpt.capstone.service.RoomService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "*", maxAge = 3600)
//public class RoomAmenitiesController {
//	private static final Logger LOGGER = LoggerFactory.getLogger(RoomAmenitiesController.class.getName());
//
//	@Autowired
//	RoomAmenitiesService roomAmenitiesService;
//	@Autowired
//	RoomService roomService;
//	@Autowired
//	AmenityService amenityService;
//
//	@GetMapping(value = "/roomAmenities/{id}")
//	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
//		ResponseObject responseObject = new ResponseObject();
//		try {
//			Long lId = Long.valueOf(id);
//			if (roomAmenitiesService.isExist(lId)) {
//				RoomAmenitiesDto roomAmenitiesDto = roomAmenitiesService.findById(lId);
//				responseObject.setResults(roomAmenitiesDto);
//				responseObject.setCode("200");
//				responseObject.setMessageCode(Message.OK);
//				LOGGER.info("getById: {}", roomAmenitiesDto);
//				return new ResponseEntity<>(responseObject, HttpStatus.OK);
//			} else {
//				LOGGER.error("getById: {}", "ID Room Amenities is not exist");
//				responseObject.setCode("404");
//				responseObject.setMessageCode(Message.NOT_FOUND);
//				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
//			}
//		} catch (NumberFormatException e) {
//			LOGGER.error("getById: {}", e);
//			responseObject.setCode("404");
//			responseObject.setMessageCode(Message.NOT_FOUND);
//			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
//		} catch (Exception ex) {
//			LOGGER.error("getById: {}", ex);
//			responseObject.setCode("500");
//			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@GetMapping(value = "/roomAmenities")
//	public ResponseEntity<ResponseObject> getAll() {
//		ResponseObject responseObject = new ResponseObject();
//		try {
//			List<RoomAmenitiesDto> roomAmenitiesDtos = roomAmenitiesService.findAll();
//			if (roomAmenitiesDtos == null || roomAmenitiesDtos.isEmpty()) {
//				responseObject.setResults(new ArrayList<>());
//			}else {
//				responseObject.setResults(roomAmenitiesDtos);
//			}
//			responseObject.setCode("200");
//			responseObject.setMessageCode(Message.OK);
//			LOGGER.info("getAll: {}", roomAmenitiesDtos);
//			return new ResponseEntity<>(responseObject, HttpStatus.OK);
//		} catch (Exception ex) {
//			LOGGER.error("getAll: {}", ex);
//			responseObject.setCode("500");
//			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@PostMapping(value = "/roomAmenities")
//	public ResponseEntity<ResponseObject> postRoomAmenities(@RequestBody RoomAmenitiesDto roomAmenitiesDto) {
//		ResponseObject response = new ResponseObject();
//		try {
//			if (roomAmenitiesDto.getId() != null
//					|| (roomAmenitiesDto.getAmenityId() == null
//							|| !amenityService.isExist(roomAmenitiesDto.getAmenityId()))
//					|| (roomAmenitiesDto.getRoomId() == null || !roomService.isExist(roomAmenitiesDto.getRoomId()))) {
//				LOGGER.error("postRoomAmenities: {}", "ID Room is not exist or Wrong body format");
//				response.setCode("406");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//
//			RoomAmenitiesDto roomAmenitiesDto2 = roomAmenitiesService.createRoomAmenities(roomAmenitiesDto);
//			if (roomAmenitiesDto2 == null) {
//				response.setCode("500");
//				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//			response.setCode("200");
//			response.setMessageCode(Message.OK);
//			response.setResults(roomAmenitiesDto2);
//			LOGGER.info("postRoomAmenities: {}", roomAmenitiesDto2);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} catch (Exception e) {
//			LOGGER.error("postRoomAmenities: {}", e);
//			response.setCode("500");
//			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@PutMapping(value = "/roomAmenities")
//	public ResponseEntity<ResponseObject> putRoomAmenities(@RequestBody RoomAmenitiesDto roomAmenitiesDto) {
//		ResponseObject response = new ResponseObject();
//		try {
//			if (roomAmenitiesDto.getId() == null || !roomAmenitiesService.isExist(roomAmenitiesDto.getId())) {
//				LOGGER.error("putRoomAmenities: {}", "ID Room Amenities is not exist");
//				response.setCode("404");
//				response.setMessageCode(Message.NOT_FOUND);
//				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//			}
//			if ((roomAmenitiesDto.getAmenityId() == null || !amenityService.isExist(roomAmenitiesDto.getAmenityId()))
//					|| (roomAmenitiesDto.getRoomId() == null || !roomService.isExist(roomAmenitiesDto.getRoomId()))) {
//				LOGGER.error("putRoomAmenities: {}", "ID Amenities or ID Room is not exist");
//				response.setCode("406");
//				response.setMessageCode(Message.NOT_ACCEPTABLE);
//				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//			}
//			RoomAmenitiesDto roomAmenitiesDto2 = roomAmenitiesService.updateRoomAmenities(roomAmenitiesDto);
//			response.setCode("200");
//			response.setMessageCode(Message.OK);
//			response.setResults(roomAmenitiesDto2);
//			LOGGER.info("putRoomAmenities: {}", roomAmenitiesDto2);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} catch (Exception e) {
//			LOGGER.error("putRoomAmenities: {}", e);
//			response.setCode("500");
//			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@DeleteMapping(value = "/roomAmenities/{id}")
//	public ResponseEntity<ResponseObject> deleteRoomAmenities(@PathVariable String id) {
//		ResponseObject response = new ResponseObject();
//		try {
//			if (id == null || id.isEmpty() || !roomAmenitiesService.isExist(Long.valueOf(id))) {
//				LOGGER.error("deleteRoomAmenities: {}", "ID Room Amenities is not exist");
//				response.setCode("404");
//				response.setMessageCode(Message.NOT_FOUND);
//				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//			}
//			response.setCode("200");
//			response.setMessageCode(Message.OK);
//			roomAmenitiesService.removeRoomAmenities(Long.valueOf(id));
//			LOGGER.info("deleteRoomAmenities: {}", "DELETED");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} catch (NumberFormatException ex) {
//			LOGGER.error("deleteRoomAmenities: {}", ex);
//			response.setCode("404");
//			response.setMessageCode(Message.NOT_FOUND);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		} catch (Exception e) {
//			LOGGER.error("deleteRoomAmenities: {}", e);
//			response.setCode("500");
//			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//}
