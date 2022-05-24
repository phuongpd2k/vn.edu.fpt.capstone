package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.TypeOfRentalDto;
import vn.edu.fpt.capstone.model.TypeOfRentalModel;
import vn.edu.fpt.capstone.repository.TypeOfRentalRepository;
import vn.edu.fpt.capstone.service.TypeOfRentalService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class TypeOfRentalServiceImpl implements TypeOfRentalService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TypeOfRentalServiceImpl.class.getName());

	@Autowired
	TypeOfRentalRepository typeOfRentalRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
//	@Cacheable("typeOfRental")
	public TypeOfRentalDto findById(Long id) {
		TypeOfRentalDto typeOfRentalDto = modelMapper.map(typeOfRentalRepository.findById(id).get(),
				TypeOfRentalDto.class);
		return typeOfRentalDto;
	}

	@Override
//	@Cacheable("listTypeOfRental")
	public List<TypeOfRentalDto> findAll() {
		List<TypeOfRentalModel> typeOfRentalModels = typeOfRentalRepository.findAll();
		if (typeOfRentalModels == null || typeOfRentalModels.isEmpty()) {
			return null;
		}
		List<TypeOfRentalDto> typeOfRentalDtos = Arrays
				.asList(modelMapper.map(typeOfRentalModels, TypeOfRentalDto[].class));
		return typeOfRentalDtos;
	}

	@Override
//	@CachePut("typeOfRental")
	public TypeOfRentalDto updateTypeOfRental(TypeOfRentalDto typeOfRentalDto) {
		TypeOfRentalModel typeOfRentalModel = modelMapper.map(typeOfRentalDto, TypeOfRentalModel.class);
		TypeOfRentalModel saveModel = typeOfRentalRepository.save(typeOfRentalModel);
		return modelMapper.map(saveModel, TypeOfRentalDto.class);
	}

	@Override
//	@CacheEvict("typeOfRental")
	public boolean removeTypeOfRental(Long id) {
		if (typeOfRentalRepository.existsById(id)) {
			typeOfRentalRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public TypeOfRentalDto createTypeOfRental(TypeOfRentalDto typeOfRentalDto) {
		try {
			TypeOfRentalModel typeOfRentalModel = modelMapper.map(typeOfRentalDto, TypeOfRentalModel.class);
//			if (typeOfRentalModel.getCreatedAt() == null || typeOfRentalModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				typeOfRentalModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				typeOfRentalModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				typeOfRentalModel.setModifiedAt(typeOfRentalModel.getCreatedAt());
//			}
//			if (typeOfRentalModel.getCreatedBy() == null || typeOfRentalModel.getCreatedBy().isEmpty()) {
//				typeOfRentalModel.setCreatedBy("SYSTEM");
//				typeOfRentalModel.setModifiedBy("SYSTEM");
//			} else {
//				typeOfRentalModel.setModifiedBy(typeOfRentalModel.getCreatedBy());
//			}
			TypeOfRentalModel saveModel = typeOfRentalRepository.save(typeOfRentalModel);
			return modelMapper.map(saveModel, TypeOfRentalDto.class);
		} catch (Exception e) {
			LOGGER.error("createTypeOfRental: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (typeOfRentalRepository.existsById(id)) {
			return true;
		}
		return false;
	}
}
