package vn.edu.fpt.capstone.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.RoomModel;
import vn.edu.fpt.capstone.repository.HouseRepository;
import vn.edu.fpt.capstone.repository.RoomCategoryRepository;
import vn.edu.fpt.capstone.repository.RoomTypeRepository;
import vn.edu.fpt.capstone.response.PageableResponse;
import vn.edu.fpt.capstone.constant.Constant;
import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class.getName());

	@Autowired
	private RoomService roomService;

	@Autowired
	private HouseRepository houseRepository;

	@Autowired
	private RoomCategoryRepository roomCategoryRepository;

	@Autowired
	private RoomTypeRepository roomTypeRepository;

	@Autowired
	private Constant constant;

	@Autowired
	private ModelMapper modelMapper;

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

	@GetMapping(value = "/room/page/{pageIndex}")
	public ResponseEntity<?> getAllRoom(@PathVariable("pageIndex") int pageIndex) {
		try {
			Page<RoomModel> page = roomService.getPage(constant.PAGE_SIZE, pageIndex);
			List<RoomDto> list = Arrays.asList(modelMapper.map(page.getContent(), RoomDto[].class));

			PageableResponse pageableResponse = new PageableResponse();
			pageableResponse.setCurrentPage(pageIndex);
			pageableResponse.setTotalPages(page.getTotalPages());
			pageableResponse.setTotalItems(page.getTotalElements());
			pageableResponse.setResults(list);

			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get all room successfully!")
							.messageCode("GET_ALL_ROOM_SUCCESSFULLY").results(pageableResponse).build());
		} catch (Exception ex) {
			LOGGER.error("Get all room: {}", ex);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Get all room: " + ex.getMessage()).messageCode("GET_ALL_ROOM_FAIL").build());
		}
	}

	@PostMapping(value = "/room")
	public ResponseEntity<?> postRoom(@RequestBody RoomDto roomDto) {
		try {
			if ((roomDto.getRoomCategory().getId() != null)
					&& (!roomCategoryRepository.findById(roomDto.getRoomCategory().getId()).isPresent())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400").message("Create room: id room category not exist!")
								.messageCode("ID_ROOM_CATEGORY_NOT_EXIST").build());
			}
			if ((roomDto.getRoomType().getId() != null)
					&& (!roomTypeRepository.findById(roomDto.getRoomType().getId()).isPresent())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create room: id room type not exist!").messageCode("ID_ROOM_TYPE_NOT_EXIST").build());
			}
			if (houseRepository.findById(roomDto.getHouse().getId()) == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create room: id house not exist!").messageCode("ID_HOUSE_NOT_EXIST").build());
			}
			RoomModel roomModel = roomService.create(roomDto);

			if (roomModel != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(ResponseObject.builder().code("200").message("Create room: successfully!")
								.messageCode("CREATE_ROOM_SUCCESSFULLY").results(roomModel).build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Create room fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room fail: " + e.getMessage()).messageCode("CREATE_ROOM_FAIL").build());
		}
	}

	@PutMapping(value = "/room")
	public ResponseEntity<?> putRoom(@RequestBody RoomDto roomDto) {
		try {
			if (roomDto.getId() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Update room fail: room id not exist!").messageCode("ID_ROOM_NOT_EXIST").build());
			}
			if ((roomDto.getRoomCategory().getId() != null)
					&& (!roomCategoryRepository.findById(roomDto.getRoomCategory().getId()).isPresent())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400").message("Update room: id room category not exist!")
								.messageCode("ID_ROOM_CATEGORY_NOT_EXIST").build());
			}
			if ((roomDto.getRoomType().getId() != null)
					&& (!roomTypeRepository.findById(roomDto.getRoomType().getId()).isPresent())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Update room: id room type not exist!").messageCode("ID_ROOM_TYPE_NOT_EXIST").build());
			}
			if (houseRepository.findById(roomDto.getHouse().getId()) == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Update room: id house not exist!").messageCode("ID_HOUSE_NOT_EXIST").build());
			}
			RoomModel roomModel = roomService.create(roomDto);

			if (roomModel != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(ResponseObject.builder().code("200").message("Update room: successfully!")
								.messageCode("UPDATE_ROOM_SUCCESSFULLY").results(roomModel).build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Create room fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Update room fail: " + e.getMessage()).messageCode("UPDATE_ROOM_FAIL").build());
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
