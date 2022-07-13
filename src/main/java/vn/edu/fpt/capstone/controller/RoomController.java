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
import vn.edu.fpt.capstone.response.RoomPostingResponse;
import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.FilterRoomDto;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.PostService;
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
	private ModelMapper modelMapper;
	
	@Autowired
	private PostService postService;

	@GetMapping(value = "/room/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (roomService.isExist(lId)) {
				RoomDto roomDto = roomService.findById(lId);
				LOGGER.info("getById: {}", "roomDto");
				return ResponseEntity.status(HttpStatus.OK)
						.body(ResponseObject.builder().code("200").message("Get room successfully!")
								.messageCode("GET_ROOM_SUCCESSFULLY").results(roomDto).build());
			} else {
				LOGGER.error("getById: {}", "ID Room is not exist");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder().code("404")
						.message("Get room Fail!").messageCode("GET_ROOM_FAIL").build());
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
	public ResponseEntity<?> getRoomPostingById(@RequestParam(required = true) Long id) {
		try {
			Long lId = Long.valueOf(id);
			RoomPostingResponse rpr = roomService.getRoomPosting(lId);
			LOGGER.info("getById: {}", "roomDto");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Get room successfully!").messageCode("GET_ROOM_SUCCESSFULLY").results(rpr).build());
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}", e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder().code("404")
					.message("Get room posting fail!").messageCode("GET_ROOM_POSTING_FAIL").build());
		} catch (Exception ex) {
			LOGGER.error("getById: {}", ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get room posting fail!").messageCode("GET_ROOM_POSTING_FAIL").build());
		}
	}

	@PostMapping(value = "/room/page")
	public ResponseEntity<?> getAllRoomByHouseId(@RequestParam(required = true) int pageIndex,
			@RequestParam(required = true) int pageSize, @RequestBody HouseDto houseDto) {
		try {
			Page<RoomModel> page = roomService.getPage(pageSize, pageIndex, houseDto.getId());
			List<RoomDto> list = Arrays.asList(modelMapper.map(page.getContent(), RoomDto[].class));

			PageableResponse pageableResponse = new PageableResponse();
			pageableResponse.setCurrentPage(pageIndex);
			pageableResponse.setPageSize(pageSize);
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
			if (houseRepository.findById(roomDto.getHouse().getId()) == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create room: id house not exist!").messageCode("ID_HOUSE_NOT_EXIST").build());
			}

			if (roomService.roomTypeAndRoomCategoryExits(roomDto.getRoomType().getId(),
					roomDto.getRoomCategory().getId(), roomDto.getHouse().getId())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400")
								.message("Create room: room type and room category existed!")
								.messageCode("TYPE_AND_CATEGORY_ROOM_EXISTED").build());
			}

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

			RoomModel roomModel = roomService.create(roomDto);

			if (roomModel != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Create room: successfully!").messageCode("CREATE_ROOM_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Create room fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room fail: " + e.getMessage()).messageCode("CREATE_ROOM_FAIL").build());
		}
	}
	
	@PostMapping(value = "/room/check-room-name")
	public ResponseEntity<?> checkRoomName(@RequestParam(required = false) Long idHouse, 
			@RequestParam(required = false) String roomName) {
		try {		
			if (roomService.checkExistRoomName(idHouse, roomName)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create room: room name existed!").messageCode("ROOM_NAME_EXISTED").build());
			}
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Name room: OK!").messageCode("ROOM_NAME_OK").build());
		} catch (Exception e) {
			LOGGER.error("Create room fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room fail: " + e.getMessage()).messageCode("CREATE_ROOM_FAIL").build());
		}
	}
	

	@PostMapping(value = "/room/check-unique")
	public ResponseEntity<?> checkRoomUnique(@RequestBody RoomDto roomDto) {
		try {
			if (houseRepository.findById(roomDto.getHouse().getId()) == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create room: id house not exist!").messageCode("ID_HOUSE_NOT_EXIST").build());
			}

			if (roomService.roomTypeAndRoomCategoryExits(roomDto.getRoomType().getId(),
					roomDto.getRoomCategory().getId(), roomDto.getHouse().getId())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ResponseObject.builder().code("400")
								.message("Create room: room type and room category existed!")
								.messageCode("TYPE_AND_CATEGORY_ROOM_EXISTED").build());
			}

			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Create room: type and category ok!").messageCode("TYPE_AND_CATEGORY_ROOM_OK").build());

		} catch (Exception e) {
			LOGGER.error("Create room fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room fail: " + e.getMessage()).messageCode("TYPE_AND_CATEGORY_ROOM_EXISTED").build());
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

			RoomDto room = roomService.findById(roomDto.getId());

			if ((room.getRoomType().getId() != roomDto.getRoomType().getId())
					|| (room.getRoomCategory().getId() != roomDto.getRoomCategory().getId())
					|| (room.getHouse().getId() != roomDto.getHouse().getId())) {
				if (roomService.roomTypeAndRoomCategoryExits(roomDto.getRoomType().getId(),
						roomDto.getRoomCategory().getId(), roomDto.getHouse().getId())) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(ResponseObject.builder().code("400")
									.message("Create room: room type and room category existed!")
									.messageCode("TYPE_AND_CATEGORY_ROOM_EXISTED").build());
				}
			}

			RoomModel roomModel = roomService.create(roomDto);

			if (roomModel != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Update room: successfully!").messageCode("UPDATE_ROOM_SUCCESSFULLY").build());
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
		try {
			if (id == null || id.isEmpty() || !roomService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteRoom: {}", "ID Room is not exist");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder().code("404")
						.message("Delete room fail: id not found").messageCode("DELETE_ROOM_FAIL").build());
			}

			roomService.deleteRoom(Long.valueOf(id));
			LOGGER.info("deleteRoom: {}", "DELETED");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Delete room: successfully!").messageCode("DELETE_ROOM_SUCCESSFULLY").build());
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteRoom: {}", ex);
			LOGGER.error("deleteRoom: {}", "ID Room is not exist");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder().code("404")
					.message("Delete room fail: " + ex.getMessage()).messageCode("DELETE_ROOM_FAIL").build());
		} catch (Exception e) {
			LOGGER.error("deleteRoom: {}", e);
			LOGGER.error("deleteRoom: {}", "ID Room is not exist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Delete room fail: " + e.getMessage()).messageCode("DELETE_ROOM_FAIL").build());
		}
	}
	
	@PostMapping(value = "/room/filter")
	public ResponseEntity<ResponseObject> historyHouse(@RequestBody FilterRoomDto dto) {
		try {
			PageableResponse pageableResponse = postService.filterPosting(dto);
			LOGGER.info("get All posting: {}", pageableResponse.getResults());
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get posting successfully")
							.messageCode("GET_POSTING_SUCCESSFULLY").results(pageableResponse).build());
		} catch (Exception e) {
			LOGGER.error("getAll posting: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get posting: " + e.getMessage()).messageCode("GET_POSTING_FAILED").build());
		}
	}

}
