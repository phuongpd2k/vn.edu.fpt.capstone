package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.RoomAmenitiesDto;
import vn.edu.fpt.capstone.model.RoomAmenitiesModel;
import vn.edu.fpt.capstone.repository.RoomAmenitiesRepository;
import vn.edu.fpt.capstone.service.RoomAmenitiesService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class RoomAmenitiesServiceImpl implements RoomAmenitiesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomAmenitiesServiceImpl.class.getName());

    @Autowired
    private RoomAmenitiesRepository roomAmenitiesRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public RoomAmenitiesDto findById(Long id) {
        RoomAmenitiesDto roomAmenitiesDto = modelMapper.map(roomAmenitiesRepository.findById(id).get(), RoomAmenitiesDto.class);
        return roomAmenitiesDto;
    }

    @Override
    public List<RoomAmenitiesDto> findAll() {
        List<RoomAmenitiesModel> roomAmenitiesModels = roomAmenitiesRepository.findAll();
        if (roomAmenitiesModels == null || roomAmenitiesModels.isEmpty()) {
            return null;
        }
        List<RoomAmenitiesDto> roomAmenitiesDtos = Arrays.asList(modelMapper.map(roomAmenitiesModels, RoomAmenitiesDto[].class));
        return roomAmenitiesDtos;
    }

    @Override
	public RoomAmenitiesDto updateRoomAmenities(RoomAmenitiesDto roomAmenitiesDto) {
		RoomAmenitiesModel roomAmenitiesModel = modelMapper.map(roomAmenitiesDto, RoomAmenitiesModel.class);
		RoomAmenitiesModel saveModel = roomAmenitiesRepository.save(roomAmenitiesModel);
		return modelMapper.map(saveModel, RoomAmenitiesDto.class);
	}

	@Override
	public boolean removeRoomAmenities(Long id) {
		if (roomAmenitiesRepository.existsById(id)) {
			roomAmenitiesRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public RoomAmenitiesDto createRoomAmenities(RoomAmenitiesDto roomAmenitiesDto) {
		try {
			RoomAmenitiesModel roomAmenitiesModel = modelMapper.map(roomAmenitiesDto, RoomAmenitiesModel.class);
//			if (roomAmenitiesModel.getCreatedAt() == null || roomAmenitiesModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				roomAmenitiesModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				roomAmenitiesModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				roomAmenitiesModel.setModifiedAt(roomAmenitiesModel.getCreatedAt());
//			}
//			if (roomAmenitiesModel.getCreatedBy() == null || roomAmenitiesModel.getCreatedBy().isEmpty()) {
//				roomAmenitiesModel.setCreatedBy("SYSTEM");
//				roomAmenitiesModel.setModifiedBy("SYSTEM");
//			} else {
//				roomAmenitiesModel.setModifiedBy(roomAmenitiesModel.getCreatedBy());
//			}
			RoomAmenitiesModel saveModel = roomAmenitiesRepository.save(roomAmenitiesModel);
			return modelMapper.map(saveModel, RoomAmenitiesDto.class);
		} catch (Exception e) {
			LOGGER.error("createRoomAmenities: {}", e);
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
        if(roomAmenitiesRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
