package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.RoomCategoryDto;
import vn.edu.fpt.capstone.model.RoomCategoryModel;
import vn.edu.fpt.capstone.repository.RoomCategoryRepository;
import vn.edu.fpt.capstone.service.RoomCategoryService;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomCategoryServiceImpl implements RoomCategoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomCategoryServiceImpl.class.getName());

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public RoomCategoryDto findById(Long id) {
        RoomCategoryDto roomCategoryDto = modelMapper.map(roomCategoryRepository.findById(id).get(), RoomCategoryDto.class);
        return roomCategoryDto;
    }

    @Override
    public List<RoomCategoryDto> findAll() {
        List<RoomCategoryModel> roomCategoryModels = roomCategoryRepository.findAllByEnableTrue();
        if (roomCategoryModels == null || roomCategoryModels.isEmpty()) {
            return null;
        }
        List<RoomCategoryDto> roomCategoryDtos = Arrays.asList(modelMapper.map(roomCategoryModels, RoomCategoryDto[].class));
        return roomCategoryDtos;
    }

    @Override
	public RoomCategoryDto updateRoomCategory(RoomCategoryDto roomCategoryDto) {
		RoomCategoryModel roomCategoryModel = modelMapper.map(roomCategoryDto, RoomCategoryModel.class);
		RoomCategoryModel saveModel = roomCategoryRepository.save(roomCategoryModel);
		return modelMapper.map(saveModel, RoomCategoryDto.class);
	}

	@Override
	public boolean removeRoomCategory(Long id) {
		if (roomCategoryRepository.existsById(id)) {
			roomCategoryRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public RoomCategoryDto createRoomCategory(RoomCategoryDto roomCategoryDto) {
		try {
			RoomCategoryModel roomCategoryModel = modelMapper.map(roomCategoryDto, RoomCategoryModel.class);
//			if (roomCategoryModel.getCreatedAt() == null || roomCategoryModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				roomCategoryModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				roomCategoryModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				roomCategoryModel.setModifiedAt(roomCategoryModel.getCreatedAt());
//			}
//			if (roomCategoryModel.getCreatedBy() == null || roomCategoryModel.getCreatedBy().isEmpty()) {
//				roomCategoryModel.setCreatedBy("SYSTEM");
//				roomCategoryModel.setModifiedBy("SYSTEM");
//			} else {
//				roomCategoryModel.setModifiedBy(roomCategoryModel.getCreatedBy());
//			}
			RoomCategoryModel saveModel = roomCategoryRepository.save(roomCategoryModel);
			return modelMapper.map(saveModel, RoomCategoryDto.class);
		} catch (Exception e) {
			LOGGER.error("createRoomCategory: {}", e);
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
        if(roomCategoryRepository.existsById(id)){
            return true;
        }
        return false;
    }

	@Override
	public void removeListRoomCategory(ListIdDto listIdDto) {
		try {
			for (Long id : listIdDto.getList()) {
				RoomCategoryModel roomCategoryModel = roomCategoryRepository.getById(id);
				if(roomCategoryModel != null) {
					roomCategoryModel.setEnable(false);
					roomCategoryModel = roomCategoryRepository.save(roomCategoryModel);
				}
			}
		} catch (Exception e) {
			LOGGER.error("deleteRoomCategoryModel: {}", e.getMessage());
		}	
	}
}
