package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ImageDto;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.ImageModel;
import vn.edu.fpt.capstone.model.RoomModel;
import vn.edu.fpt.capstone.repository.ImageRepository;
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.ArrayList;
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
	public RoomModel create(RoomDto roomDto) {
		List<ImageDto> list = new ArrayList<ImageDto>();
		for (ImageDto imageDto : roomDto.getImages()) {
			//ImageDto imageDtoNew = imageServiceImpl.getImageByUrl(imageDto.getImageUrl());
			if(imageDto.getId() != null) {
				list.add(imageDto);
			}else {
				ImageDto imageDtoNew = imageServiceImpl.createImage(imageDto);
				list.add(imageDtoNew);		
			}	
		}
		roomDto.setImages(list);
		RoomModel roomModel = modelMapper.map(roomDto, RoomModel.class);
		roomModel.setEnable(true);
		return roomRepository.save(roomModel);
	}

//	@Override
//	public Page<RoomDto> getPage(int pageSize, int pageIndex) {
//		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
//		return roomRepository.getListPage(pageable);
//	}

	@Override
	public Page<RoomModel> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return roomRepository.findAll(pageable);
	}

}
