package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.model.AmenityModel;
import vn.edu.fpt.capstone.repository.AmenityRepository;
import vn.edu.fpt.capstone.service.AmenityService;

import java.util.Arrays;
import java.util.List;

@Service
public class AmenityServiceImpl implements AmenityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AmenityServiceImpl.class.getName());

	@Autowired
	private AmenityRepository amenityRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public AmenityDto findById(Long id) {
		AmenityDto amenityDto = modelMapper.map(amenityRepository.findById(id).get(), AmenityDto.class);
		return amenityDto;
	}

	@Override
	public List<AmenityDto> findAll() {
		List<AmenityModel> amenityModels = amenityRepository.findAllByEnableTrue();
		if (amenityModels == null || amenityModels.isEmpty()) {
			return null;
		}
		List<AmenityDto> amenityDtos = Arrays.asList(modelMapper.map(amenityModels, AmenityDto[].class));
		return amenityDtos;
	}

	@Override
	public AmenityDto updateAmenity(AmenityDto amenityDto) {
		AmenityModel amenityModel = modelMapper.map(amenityDto, AmenityModel.class);
		AmenityModel saveModel = amenityRepository.save(amenityModel);
		return modelMapper.map(saveModel, AmenityDto.class);
	}

	@Override
	public boolean removeAmenity(Long id) {
		if (amenityRepository.existsById(id)) {
			amenityRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public AmenityDto createAmenity(AmenityDto amenityDto) {
		try {
			AmenityModel amenityModel = modelMapper.map(amenityDto, AmenityModel.class);
//			if (amenityModel.getCreatedAt() == null || amenityModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				amenityModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				amenityModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				amenityModel.setModifiedAt(amenityModel.getCreatedAt());
//			}
//			if (amenityModel.getCreatedBy() == null || amenityModel.getCreatedBy().isEmpty()) {
//				amenityModel.setCreatedBy("SYSTEM");
//				amenityModel.setModifiedBy("SYSTEM");
//			} else {
//				amenityModel.setModifiedBy(amenityModel.getCreatedBy());
//			}
			AmenityModel saveModel = amenityRepository.save(amenityModel);
			return modelMapper.map(saveModel, AmenityDto.class);
		} catch (Exception e) {
			LOGGER.error("createAmenity: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (amenityRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	@Override
	public void removeListAmenity(ListIdDto listIdDto) {
		try {
			for (Long id : listIdDto.getList()) {
				AmenityModel amenityModel = amenityRepository.getById(id);
				if(amenityModel != null) {
					amenityModel.setEnable(false);
					amenityModel = amenityRepository.save(amenityModel);
				}
			}
		} catch (Exception e) {
			LOGGER.error("deleteAmenity: {}", e.getMessage());
		}	
	}


}
