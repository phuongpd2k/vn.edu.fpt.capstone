//package vn.edu.fpt.capstone.service.impl;
//
//import org.modelmapper.ModelMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import vn.edu.fpt.capstone.dto.HouseAmenitiesDto;
//import vn.edu.fpt.capstone.model.AmenityModel;
//import vn.edu.fpt.capstone.repository.HouseAmenitiesRepository;
//import vn.edu.fpt.capstone.service.HouseAmenitiesService;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//public class HouseAmenitiesServiceImpl implements HouseAmenitiesService {
//	private static final Logger LOGGER = LoggerFactory.getLogger(HouseAmenitiesServiceImpl.class.getName());
//
//	@Autowired
//	private HouseAmenitiesRepository houseAmenitiesRepository;
//	@Autowired
//	ModelMapper modelMapper;
//
//	@Override
//	public HouseAmenitiesDto findById(Long id) {
//		HouseAmenitiesDto houseAmenitiesDto = modelMapper.map(houseAmenitiesRepository.findById(id).get(),
//				HouseAmenitiesDto.class);
//		return houseAmenitiesDto;
//	}
//
//	@Override
//	public List<HouseAmenitiesDto> findAll() {
//		List<HouseAmenitiesModel> houseAmenitiesModels = houseAmenitiesRepository.findAll();
//		if (houseAmenitiesModels == null || houseAmenitiesModels.isEmpty()) {
//			return null;
//		}
//		List<HouseAmenitiesDto> houseAmenitiesDtos = Arrays
//				.asList(modelMapper.map(houseAmenitiesModels, HouseAmenitiesDto[].class));
//		return houseAmenitiesDtos;
//	}
//
//	@Override
//	public HouseAmenitiesDto updateHouseAmenities(HouseAmenitiesDto houseAmenitiesDto) {
//		HouseAmenitiesModel houseAmenitiesModel = modelMapper.map(houseAmenitiesDto, HouseAmenitiesModel.class);
//		HouseAmenitiesModel saveModel = houseAmenitiesRepository.save(houseAmenitiesModel);
//		return modelMapper.map(saveModel, HouseAmenitiesDto.class);
//	}
//
//	@Override
//	public boolean removeHouseAmenities(Long id) {
//		if (houseAmenitiesRepository.existsById(id)) {
//			houseAmenitiesRepository.deleteById(id);
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public HouseAmenitiesDto createHouseAmenities(HouseAmenitiesDto houseAmenitiesDto) {
//		try {
//			HouseAmenitiesModel houseAmenitiesModel = modelMapper.map(houseAmenitiesDto, HouseAmenitiesModel.class);
//			if (houseAmenitiesModel.getCreatedAt() == null || houseAmenitiesModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				houseAmenitiesModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				houseAmenitiesModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				houseAmenitiesModel.setModifiedAt(houseAmenitiesModel.getCreatedAt());
//			}
//			if (houseAmenitiesModel.getCreatedBy() == null || houseAmenitiesModel.getCreatedBy().isEmpty()) {
//				houseAmenitiesModel.setCreatedBy("SYSTEM");
//				houseAmenitiesModel.setModifiedBy("SYSTEM");
//			} else {
//				houseAmenitiesModel.setModifiedBy(houseAmenitiesModel.getCreatedBy());
//			}
//			HouseAmenitiesModel saveModel = houseAmenitiesRepository.save(houseAmenitiesModel);
//			return modelMapper.map(saveModel, HouseAmenitiesDto.class);
//		} catch (Exception e) {
//			LOGGER.error("createHouseAmenities: {}", e);
//			return null;
//		}
//	}
//
//	@Override
//	public boolean isExist(Long id) {
//		if (houseAmenitiesRepository.existsById(id)) {
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public List<HouseAmenitiesDto> createHouseAmenities(List<HouseAmenitiesDto> houseAmenitiesDtos) {
//		List<HouseAmenitiesModel> houseAmenityModels = new ArrayList<HouseAmenitiesModel>();
//		for (HouseAmenitiesDto houseAmenityDto : houseAmenitiesDtos) {
//			houseAmenityModels
//					.add(houseAmenitiesRepository.save(modelMapper.map(houseAmenityDto, HouseAmenitiesModel.class)));
//		}
//		List<HouseAmenitiesDto> result = Arrays.asList(modelMapper.map(houseAmenityModels, HouseAmenitiesDto[].class));
//		return result;
//	}
//}
