package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.ImageDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.AmenityModel;
import vn.edu.fpt.capstone.model.ImageModel;
import vn.edu.fpt.capstone.model.RoomModel;
import vn.edu.fpt.capstone.repository.AmenityRepository;
import vn.edu.fpt.capstone.repository.HouseRepository;
import vn.edu.fpt.capstone.repository.ImageRepository;
import vn.edu.fpt.capstone.repository.RoomCategoryRepository;
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.repository.RoomTypeRepository;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class.getName());

	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private HouseRepository houseRepository;
	@Autowired
	private RoomCategoryRepository roomCategoryRepository;
	@Autowired
	private RoomTypeRepository roomTypeRepository;
	@Autowired
	private AmenityRepository amenityRepository;
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public RoomDto findById(Long id) {
		RoomDto roomDto = modelMapper.map(roomRepository.findById(id).get(), RoomDto.class);
		return roomDto;
	}

	@Override
	public List<RoomDto> findAll() {
		List<RoomModel> roomModels = roomRepository.findAll();
		if (roomModels == null || roomModels.isEmpty()) {
			return null;
		}
		List<RoomDto> roomDtos = roomModels.stream().map(roomModel -> modelMapper.map(roomModel, RoomDto.class))
				.collect(Collectors.toList());
		return roomDtos;
	}

	@Override
	public boolean removeRoom(Long id) {
		if (roomRepository.existsById(id)) {
			roomRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public boolean isExist(Long id) {
		if (roomRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseEntity<?> create(RoomDto roomDto) {
		if ((roomDto.getRoomCategoryId() != null)
				&& (!roomCategoryRepository.findById(roomDto.getRoomCategoryId()).isPresent())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("Create room: id room category not exist!")
							.messageCode("ID_ROOM_CATEGORY_NOT_EXIST").build());
		}
		if ((roomDto.getRoomTypeId() != null) && (!roomTypeRepository.findById(roomDto.getRoomTypeId()).isPresent())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room: id room type not exist!").messageCode("ID_ROOM_TYPE_NOT_EXIST").build());
		}
		if (houseRepository.findById(roomDto.getHouseId()) == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room: id house not exist!").messageCode("ID_HOUSE_NOT_EXIST").build());
		}
		RoomModel roomModel = modelMapper.map(roomDto, RoomModel.class);
		roomModel.setRoomType(roomTypeRepository.findById(roomDto.getRoomTypeId()).get());
		roomModel.setRoomCategory(roomCategoryRepository.findById(roomDto.getRoomCategoryId()).get());
		roomModel.setHouse(houseRepository.findById(roomDto.getHouseId()).get());

		Set<AmenityModel> amenities = new HashSet<>();
		for (AmenityDto amenityDto : roomDto.getAmenities()) {
			amenities.add(modelMapper.map(amenityDto, AmenityModel.class));
		}
		roomModel.setAmenities(amenities);
		
		Set<ImageModel> images = new HashSet<>();
		for (ImageDto imageDto : roomDto.getImages()) {
			images.add(modelMapper.map(imageDto, ImageModel.class));
		}
		roomModel.setImages(images);

		try {
			roomModel = roomRepository.save(roomModel);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room fail: " + e.getMessage()).messageCode("CREATE_ROOM_FAIL").build());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(ResponseObject.builder().code("200").message("Create room: successfully!")
						.messageCode("CREATE_ROOM_SUCCESSFULLY").results(roomModel).build());
	}

	@Override
	public ResponseEntity<?> update(RoomDto roomDto) {
		if (roomDto.getId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Update room fail: room id not exist!").messageCode("ID_ROOM_NOT_EXIST").build());
		}

		if ((roomDto.getRoomCategoryId() != null)
				&& (!roomCategoryRepository.findById(roomDto.getRoomCategoryId()).isPresent())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("Update room: id room category not exist!")
							.messageCode("ID_ROOM_CATEGORY_NOT_EXIST").build());
		}
		if ((roomDto.getRoomTypeId() != null) && (!roomTypeRepository.findById(roomDto.getRoomTypeId()).isPresent())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Update room: id room type not exist!").messageCode("ID_ROOM_TYPE_NOT_EXIST").build());
		}
		if (!houseRepository.findById(roomDto.getHouseId()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Update room: id house not exist!").messageCode("ID_HOUSE_NOT_EXIST").build());
		}

		RoomModel roomModel = modelMapper.map(roomDto, RoomModel.class);
		roomModel.setId(roomDto.getId());
		roomModel.setRoomType(roomTypeRepository.findById(roomDto.getRoomTypeId()).get());
		roomModel.setRoomCategory(roomCategoryRepository.findById(roomDto.getRoomCategoryId()).get());
		roomModel.setHouse(houseRepository.findById(roomDto.getHouseId()).get());
		
		Set<AmenityModel> amenities = new HashSet<>();
		for (AmenityDto amenityDto : roomDto.getAmenities()) {
			amenities.add(modelMapper.map(amenityDto, AmenityModel.class));
		}
		roomModel.setAmenities(amenities);
		
		Set<ImageModel> images = new HashSet<>();
		for (ImageDto imageDto : roomDto.getImages()) {
			images.add(modelMapper.map(imageDto, ImageModel.class));
		}
		roomModel.setImages(images);

		// RoomModel roomModel = convertDtoToEntity(roomDto);
		// roomModel.setId(roomDto.getId());

//		Set<AmenityModel> amenities = new HashSet<AmenityModel>();
//		for (AmenityDto amenityDto : roomDto.getAmenities()) {
//			AmenityModel am = modelMapper.map(amenityDto, AmenityModel.class);
//			amenities.add(am);
//		}
//		roomModel.setAmenities(amenities);
//		
//		Set<ImageModel> images = new HashSet<ImageModel>();
//		for (ImageDto imageDto : roomDto.getImages()) {
//			ImageModel im = modelMapper.map(imageDto, ImageModel.class);
//			images.add(im);
//		}
//		roomModel.setImages(images);

		try {
			roomModel = roomRepository.save(roomModel);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Update room fail: " + e.getMessage()).messageCode("UPDATE_ROOM_FAIL").build());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(ResponseObject.builder().code("200").message("Update room: successfully!")
						.messageCode("UPDATE_ROOM_SUCCESSFULLY").results(roomModel).build());
	}
}
