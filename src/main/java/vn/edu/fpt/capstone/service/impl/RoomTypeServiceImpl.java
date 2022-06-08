package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.RoomTypeDto;
import vn.edu.fpt.capstone.model.RoomTypeModel;
import vn.edu.fpt.capstone.repository.RoomTypeRepository;
import vn.edu.fpt.capstone.service.RoomTypeService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomTypeServiceImpl.class.getName());

    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public RoomTypeDto findById(Long id) {
        RoomTypeDto roomTypeDto = modelMapper.map(roomTypeRepository.findById(id).get(), RoomTypeDto.class);
        return roomTypeDto;
    }

    @Override
    public List<RoomTypeDto> findAll() {
        List<RoomTypeModel> roomTypeModels = roomTypeRepository.findAllByEnableTrue();
        if (roomTypeModels == null || roomTypeModels.isEmpty()) {
            return null;
        }
        List<RoomTypeDto> roomTypeDtos = Arrays.asList(modelMapper.map(roomTypeModels, RoomTypeDto[].class));
        return roomTypeDtos;
    }

    @Override
	public RoomTypeDto updateRoomType(RoomTypeDto roomTypeDto) {
		RoomTypeModel roomTypeModel = modelMapper.map(roomTypeDto, RoomTypeModel.class);
		RoomTypeModel saveModel = roomTypeRepository.save(roomTypeModel);
		return modelMapper.map(saveModel, RoomTypeDto.class);
	}

	@Override
	public boolean removeRoomType(Long id) {
		if (roomTypeRepository.existsById(id)) {
			roomTypeRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public RoomTypeDto createRoomType(RoomTypeDto roomTypeDto) {
		try {
			RoomTypeModel roomTypeModel = modelMapper.map(roomTypeDto, RoomTypeModel.class);
//			if (roomTypeModel.getCreatedAt() == null || roomTypeModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				roomTypeModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				roomTypeModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				roomTypeModel.setModifiedAt(roomTypeModel.getCreatedAt());
//			}
//			if (roomTypeModel.getCreatedBy() == null || roomTypeModel.getCreatedBy().isEmpty()) {
//				roomTypeModel.setCreatedBy("SYSTEM");
//				roomTypeModel.setModifiedBy("SYSTEM");
//			} else {
//				roomTypeModel.setModifiedBy(roomTypeModel.getCreatedBy());
//			}
			RoomTypeModel saveModel = roomTypeRepository.save(roomTypeModel);
			return modelMapper.map(saveModel, RoomTypeDto.class);
		} catch (Exception e) {
			LOGGER.error("createRoomType: {}", e);
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
        if(roomTypeRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
