package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.RoomModel;
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class.getName());

	@Autowired
	RoomRepository roomRepository;
	@Autowired
	ModelMapper modelMapper;

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
	public RoomDto updateRoom(RoomDto roomDto) {
		try {
			RoomModel roomModel = modelMapper.map(roomDto, RoomModel.class);
//			if (roomModel.getModifiedBy() == null || roomModel.getModifiedBy().isEmpty()) {
//				TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				formatter.setTimeZone(timeZone);
//				Date date = new Date();
//				roomModel.setModifiedAt(formatter.parse(formatter.format(date)));
//				roomModel.setModifiedBy("SYSTEM");
//			}
			RoomModel saveModel = roomRepository.save(roomModel);
			return modelMapper.map(saveModel, RoomDto.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return null;
		}
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
	public RoomDto createRoom(RoomDto roomDto) {
		try {
			RoomModel roomModel = modelMapper.map(roomDto, RoomModel.class);
//			if (roomModel.getCreatedAt() == null || roomModel.getCreatedAt().toString().isEmpty()) {
//				TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				formatter.setTimeZone(timeZone);
//				Date date = new Date();
//				roomModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				roomModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				roomModel.setModifiedAt(roomModel.getCreatedAt());
//			}
//			if (roomModel.getCreatedBy() == null || roomModel.getCreatedBy().isEmpty()) {
//				roomModel.setCreatedBy("SYSTEM");
//				roomModel.setModifiedBy("SYSTEM");
//			} else {
//				roomModel.setModifiedBy(roomModel.getCreatedBy());
//			}
			RoomModel saveModel = roomRepository.save(roomModel);
			return modelMapper.map(saveModel, RoomDto.class);
		} catch (Exception e) {
			LOGGER.error("createRoom: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (roomRepository.existsById(id)) {
			return true;
		}
		return false;
	}
}
