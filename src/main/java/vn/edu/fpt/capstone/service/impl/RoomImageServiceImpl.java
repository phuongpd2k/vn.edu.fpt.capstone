package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.RoomImageDto;
import vn.edu.fpt.capstone.model.RoomImageModel;
import vn.edu.fpt.capstone.repository.RoomImageRepository;
import vn.edu.fpt.capstone.service.RoomImageService;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomImageServiceImpl implements RoomImageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomImageServiceImpl.class.getName());

    @Autowired
    private RoomImageRepository roomImageRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public RoomImageDto findById(Long id) {
        RoomImageDto roomImageDto = modelMapper.map(roomImageRepository.findById(id).get(), RoomImageDto.class);
        return roomImageDto;
    }

    @Override
    public List<RoomImageDto> findAll() {
        List<RoomImageModel> roomImageModels = roomImageRepository.findAll();
        if (roomImageModels == null || roomImageModels.isEmpty()) {
            return null;
        }
        List<RoomImageDto> roomImageDtos = Arrays.asList(modelMapper.map(roomImageModels, RoomImageDto[].class));
        return roomImageDtos;
    }

    @Override
	public RoomImageDto updateRoomImage(RoomImageDto roomImageDto) {
		RoomImageModel roomImageModel = modelMapper.map(roomImageDto, RoomImageModel.class);
		RoomImageModel saveModel = roomImageRepository.save(roomImageModel);
		return modelMapper.map(saveModel, RoomImageDto.class);
	}

	@Override
	public boolean removeRoomImage(Long id) {
		if (roomImageRepository.existsById(id)) {
			roomImageRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public RoomImageDto createRoomImage(RoomImageDto roomImageDto) {
		try {
			RoomImageModel roomImageModel = modelMapper.map(roomImageDto, RoomImageModel.class);
//			if (roomImageModel.getCreatedAt() == null || roomImageModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				roomImageModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				roomImageModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				roomImageModel.setModifiedAt(roomImageModel.getCreatedAt());
//			}
//			if (roomImageModel.getCreatedBy() == null || roomImageModel.getCreatedBy().isEmpty()) {
//				roomImageModel.setCreatedBy("SYSTEM");
//				roomImageModel.setModifiedBy("SYSTEM");
//			} else {
//				roomImageModel.setModifiedBy(roomImageModel.getCreatedBy());
//			}
			RoomImageModel saveModel = roomImageRepository.save(roomImageModel);
			return modelMapper.map(saveModel, RoomImageDto.class);
		} catch (Exception e) {
			LOGGER.error("createRoomImage: {}", e);
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
        if(roomImageRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
