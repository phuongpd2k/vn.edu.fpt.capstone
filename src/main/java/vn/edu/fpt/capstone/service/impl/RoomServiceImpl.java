package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.CategoryTypeDto;
import vn.edu.fpt.capstone.dto.ImageDto;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.AmenityModel;
import vn.edu.fpt.capstone.model.RoomModel;
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.response.RoomPostingResponse;
import vn.edu.fpt.capstone.service.QuanHuyenService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.ThanhPhoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private ImageServiceImpl imageServiceImpl;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private QuanHuyenService quanHuyenService;

	@Autowired
	private ThanhPhoService thanhPhoService;

	@Override
	public RoomDto findById(Long id) {
		RoomModel r = roomRepository.findById(id).get();
		RoomDto roomDto = modelMapper.map(r, RoomDto.class);
		if(r.getPosts().size() > 0) {
			roomDto.setCheck(true);
		}
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
	public RoomModel create(RoomDto roomDto) {
		List<ImageDto> list = new ArrayList<ImageDto>();
		for (ImageDto imageDto : roomDto.getImages()) {
			// ImageDto imageDtoNew =
			// imageServiceImpl.getImageByUrl(imageDto.getImageUrl());
			if (imageDto.getId() != null) {
				list.add(imageDto);
			} else {
				ImageDto imageDtoNew = imageServiceImpl.createImage(imageDto);
				list.add(imageDtoNew);
			}
		}
		roomDto.setImages(list);
		RoomModel roomModel = modelMapper.map(roomDto, RoomModel.class);
		roomModel.setEnable(true);
		return roomRepository.save(roomModel);
	}

	@Override
	public Page<RoomModel> getPage(int pageSize, int pageIndex, String name, Long houseId) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return roomRepository.getListPage(houseId, name, pageable);
	}

	@Override
	public void deleteRoom(Long id) {
		RoomModel roomModel = roomRepository.getById(id);
		roomModel.setEnable(false);
		roomRepository.save(roomModel);
	}

	@Override
	public int maxPrice(Long idHouse) {
		return roomRepository.getMaxPrice(idHouse);
	}

	@Override
	public int minPrice(Long idHouse) {
		return roomRepository.getMinPrice(idHouse);
	}

	@Override
	public float minArea(Long idHouse) {
		return roomRepository.getMinArea(idHouse);
	}

	@Override
	public float maxArea(Long idHouse) {
		return roomRepository.getMaxArea(idHouse);
	}

	@Override
	public RoomPostingResponse getRoomPosting(Long lId) {
		RoomModel roomModel = roomRepository.getByIdAndEnable(lId);
		if (roomModel != null) {
			return convert2RoomPostingResponse(roomModel);
		}
		return null;
	}

	private RoomPostingResponse convert2RoomPostingResponse(RoomModel roomModel) {
		RoomPostingResponse rp = new RoomPostingResponse();

		rp.setId(roomModel.getId());

		rp.setImages(Arrays.asList(modelMapper.map(roomModel.getImages(), ImageDto[].class)));
		rp.setRoomName(roomModel.getName());
		rp.setRoomType(roomModel.getRoomType().getName());
		rp.setArea(roomModel.getArea());
		rp.setMaximumNumberOfPeople(roomModel.getMaximumNumberOfPeople());
		rp.setRentalPrice(roomModel.getRentalPrice());
		rp.setStatusRental(roomModel.isStatus_rental());
		rp.setElectricityPriceByNumber(roomModel.getElectricityPriceByNumber());
		rp.setWaterPricePerMonth(roomModel.getWaterPricePerMonth());
		rp.setRoomDescription(roomModel.getDescription());
		rp.setFloor(roomModel.getFloor());
		rp.setTimeType(roomModel.getType());
		rp.setLinkFb(roomModel.getHouse().getLinkFb());
		rp.setCreatedDate(roomModel.getCreatedDate());

		rp.setStreet(roomModel.getHouse().getAddress().getStreet());
		rp.setPhuongXa(roomModel.getHouse().getAddress().getPhuongXa().getName());
		QuanHuyenDto dto = new QuanHuyenDto();
		dto = quanHuyenService.findById(roomModel.getHouse().getAddress().getPhuongXa().getMaQh());
		rp.setQuanHuyen(dto.getName());
		rp.setThanhPho(thanhPhoService.findById(dto.getMaTp()).getName());

		List<AmenityModel> listARoom = new ArrayList<AmenityModel>();
		for (AmenityModel model : roomModel.getAmenities()) {
			if (model.isEnable() == true) {
				listARoom.add(model);
			}
		}
		rp.setAmenitiesRoom(Arrays.asList(modelMapper.map(listARoom, AmenityDto[].class)));

		rp.setRoomCategoryName(roomModel.getRoomCategory().getName());
		rp.setRoomCategoryDescription(roomModel.getRoomCategory().getDescription());

		rp.setHouseName(roomModel.getHouse().getName());
		rp.setHouseDescription(roomModel.getHouse().getDescription());
		rp.setHostName(roomModel.getHouse().getUser().getFullName());
		rp.setHostPhone(roomModel.getHouse().getUser().getPhoneNumber());
		rp.setImageLinkHost(roomModel.getHouse().getUser().getImageLink());
		List<AmenityModel> listAHouse = new ArrayList<AmenityModel>();
		for (AmenityModel model : roomModel.getHouse().getAmenities()) {
			if (model.isEnable() == true) {
				listAHouse.add(model);
			}
		}
		rp.setAmenitiesHouse(Arrays.asList(modelMapper.map(listAHouse, AmenityDto[].class)));

		List<RoomModel> listRooms = roomRepository.getAllRoomOfHouse(roomModel.getHouse().getId());
		List<CategoryTypeDto> listTypeCategory = new ArrayList<CategoryTypeDto>();
		for (RoomModel r : listRooms) {
			CategoryTypeDto categoryTypeDto = new CategoryTypeDto();
			categoryTypeDto.setRoomId(r.getId());
			categoryTypeDto.setCategoryType(r.getRoomCategory().getName() + " - " + r.getRoomType().getName());
			listTypeCategory.add(categoryTypeDto);
		}
		rp.setCategoryTypes(listTypeCategory);
		
		//rp.setStartDate(roomModel.getPosts());
		
		return rp;
	}

	@Override
	public boolean roomTypeAndRoomCategoryExits(Long roomType, Long roomCategory, Long houseId) {
		RoomModel roomModel = roomRepository.getRoomByTypeAndCategory(roomType, roomCategory, houseId);
		if (roomModel != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<RoomDto> getRoomFavoriteByUserId(Long userId) {
		List<RoomModel> roomModels = roomRepository.getRoomFavoriteByUserId(userId);
		if (roomModels == null || roomModels.isEmpty()) {
			return null;
		}
		List<RoomDto> roomDtos = roomModels.stream().map(roomModel -> modelMapper.map(roomModel, RoomDto.class))
				.collect(Collectors.toList());
		return roomDtos;
	}

	@Override
	public boolean checkExistRoomName(Long id, String name) {
		return roomRepository.checkExistRoomName(id, name);
	}

	@Override
	public void update(RoomDto roomDto) {
		RoomModel roomModel = modelMapper.map(roomDto, RoomModel.class);
		roomRepository.save(roomModel);
		
	}

}
