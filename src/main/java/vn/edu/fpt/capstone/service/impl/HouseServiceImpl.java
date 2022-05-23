package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.model.HouseModel;
import vn.edu.fpt.capstone.repository.HouseRepository;
import vn.edu.fpt.capstone.service.HouseService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class HouseServiceImpl implements HouseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseServiceImpl.class.getName());

    @Autowired
    HouseRepository houseRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public HouseDto findById(Long id) {
        HouseDto houseDto = modelMapper.map(houseRepository.findById(id).get(), HouseDto.class);
        return houseDto;
    }

    @Override
    public List<HouseDto> findAll() {
        List<HouseModel> houseModels = houseRepository.findAll();
        if (houseModels == null || houseModels.isEmpty()) {
            return null;
        }
        List<HouseDto> houseDtos = Arrays.asList(modelMapper.map(houseModels, HouseDto[].class));
        return houseDtos;
    }

    @Override
	public HouseDto updateHouse(HouseDto houseDto) {
		HouseModel houseModel = modelMapper.map(houseDto, HouseModel.class);
		HouseModel saveModel = houseRepository.save(houseModel);
		return modelMapper.map(saveModel, HouseDto.class);
	}

	@Override
	public boolean removeHouse(Long id) {
		if (houseRepository.existsById(id)) {
			houseRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public HouseDto createHouse(HouseDto houseDto) {
		try {
			HouseModel houseModel = modelMapper.map(houseDto, HouseModel.class);
//			if (boardingHouseModel.getCreatedAt() == null || boardingHouseModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				boardingHouseModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				boardingHouseModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				boardingHouseModel.setModifiedAt(boardingHouseModel.getCreatedAt());
//			}
//			if (boardingHouseModel.getCreatedBy() == null || boardingHouseModel.getCreatedBy().isEmpty()) {
//				boardingHouseModel.setCreatedBy("SYSTEM");
//				boardingHouseModel.setModifiedBy("SYSTEM");
//			} else {
//				boardingHouseModel.setModifiedBy(boardingHouseModel.getCreatedBy());
//			}
			HouseModel saveModel = houseRepository.save(houseModel);
			return modelMapper.map(saveModel, HouseDto.class);
		} catch (Exception e) {
			LOGGER.error("createHouse: {}", e);
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
    	if(houseRepository.existsById(id)){
            return true;
        }
        return false;
    }


}
