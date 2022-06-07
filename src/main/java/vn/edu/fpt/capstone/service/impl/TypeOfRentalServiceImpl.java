package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.TypeOfRentalDto;
import vn.edu.fpt.capstone.model.TypeOfRentalModel;
import vn.edu.fpt.capstone.repository.TypeOfRentalRepository;
import vn.edu.fpt.capstone.service.TypeOfRentalService;

import java.util.Arrays;
import java.util.List;

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

	@Override
	public void removeListTypeOfRental(ListIdDto listIdDto) {
		try {
			for (Long id : listIdDto.getList()) {
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
