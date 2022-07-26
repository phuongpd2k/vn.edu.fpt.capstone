package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.RoomRentalHistoryDto;
import vn.edu.fpt.capstone.model.RoomRentalHistoryModel;
import vn.edu.fpt.capstone.repository.RoomRentalHistoryRepository;
import vn.edu.fpt.capstone.service.RoomRentalHistoryService;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomRentalHistoryServiceImpl implements RoomRentalHistoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomRentalHistoryServiceImpl.class.getName());

    @Autowired
    RoomRentalHistoryRepository roomRentalHistoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RoomRentalHistoryDto findById(Long id) {
        RoomRentalHistoryDto roomRentalHistoryDto = modelMapper.map(roomRentalHistoryRepository.findById(id).get(), RoomRentalHistoryDto.class);
        return roomRentalHistoryDto;
    }

    @Override
    public List<RoomRentalHistoryDto> findAll() {
        List<RoomRentalHistoryModel> roomRentalHistoryModels = roomRentalHistoryRepository.findAll();
        if (roomRentalHistoryModels == null || roomRentalHistoryModels.isEmpty()) {
            return null;
        }
        List<RoomRentalHistoryDto> roomRentalHistoryDtos = Arrays.asList(modelMapper.map(roomRentalHistoryModels, RoomRentalHistoryDto[].class));
        return roomRentalHistoryDtos;
    }

    @Override
	public RoomRentalHistoryDto updateRoomRentalHistory(RoomRentalHistoryDto roomRentalHistoryDto) {
		RoomRentalHistoryModel roomRentalHistoryModel = modelMapper.map(roomRentalHistoryDto, RoomRentalHistoryModel.class);
		RoomRentalHistoryModel saveModel = roomRentalHistoryRepository.save(roomRentalHistoryModel);
		return modelMapper.map(saveModel, RoomRentalHistoryDto.class);
	}

	@Override
	public boolean removeRoomRentalHistory(Long id) {
		if (roomRentalHistoryRepository.existsById(id)) {
			roomRentalHistoryRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public RoomRentalHistoryDto createRoomRentalHistory(RoomRentalHistoryDto roomRentalHistoryDto) {
		try {
			RoomRentalHistoryModel roomRentalHistoryModel = modelMapper.map(roomRentalHistoryDto, RoomRentalHistoryModel.class);
//			if (roomRentalHistoryModel.getCreatedAt() == null || roomRentalHistoryModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				roomRentalHistoryModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				roomRentalHistoryModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				roomRentalHistoryModel.setModifiedAt(roomRentalHistoryModel.getCreatedAt());
//			}
//			if (roomRentalHistoryModel.getCreatedBy() == null || roomRentalHistoryModel.getCreatedBy().isEmpty()) {
//				roomRentalHistoryModel.setCreatedBy("SYSTEM");
//				roomRentalHistoryModel.setModifiedBy("SYSTEM");
//			} else {
//				roomRentalHistoryModel.setModifiedBy(roomRentalHistoryModel.getCreatedBy());
//			}
			RoomRentalHistoryModel saveModel = roomRentalHistoryRepository.save(roomRentalHistoryModel);
			return modelMapper.map(saveModel, RoomRentalHistoryDto.class);
		} catch (Exception e) {
			LOGGER.error("createRoomRentalHistory: {}", e);
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
        if(roomRentalHistoryRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
