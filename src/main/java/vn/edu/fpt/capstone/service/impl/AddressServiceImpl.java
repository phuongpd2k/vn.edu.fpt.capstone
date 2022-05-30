package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.fpt.capstone.dto.AddressDto;
import vn.edu.fpt.capstone.model.AddressModel;
import vn.edu.fpt.capstone.repository.AddressRepository;
import vn.edu.fpt.capstone.service.AddressService;

import java.util.Arrays;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class.getName());

	@Autowired
	AddressRepository addressRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
//	@Cacheable("address")
	public AddressDto findById(Long id) {
		AddressDto addressDto = modelMapper.map(addressRepository.findById(id).get(), AddressDto.class);
		return addressDto;
	}

	@Override
//	@Cacheable("listAddress")
	public List<AddressDto> findAll() {
		List<AddressModel> boardingHouseModels = addressRepository.findAll();
		if (boardingHouseModels == null || boardingHouseModels.isEmpty()) {
			return null;
		}
		List<AddressDto> addressDtos = Arrays.asList(modelMapper.map(boardingHouseModels, AddressDto[].class));
		return addressDtos;
	}

	@Override
//	@CachePut("address")
	public AddressDto updateAddress(AddressDto addressDto) {
		AddressModel addressModel = modelMapper.map(addressDto, AddressModel.class);
		AddressModel saveModel = addressRepository.save(addressModel);
		return modelMapper.map(saveModel, AddressDto.class);
	}

	@Override
//	@CacheEvict("address")
	public boolean removeAddress(Long id) {
		if (addressRepository.existsById(id)) {
			addressRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public AddressDto createAddress(AddressDto addressDto) {
		try {
			AddressModel addressModel = modelMapper.map(addressDto, AddressModel.class);
//			if (addressModel.getCreatedAt() == null || addressModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				addressModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				addressModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				addressModel.setModifiedAt(addressModel.getCreatedAt());
//			}
//			if (addressModel.getCreatedBy() == null || addressModel.getCreatedBy().isEmpty()) {
//				addressModel.setCreatedBy("SYSTEM");
//				addressModel.setModifiedBy("SYSTEM");
//			} else {
//				addressModel.setModifiedBy(addressModel.getCreatedBy());
//			}
			AddressModel saveModel = addressRepository.saveAndFlush(addressModel);
			return modelMapper.map(saveModel, AddressDto.class);
		} catch (Exception e) {
			LOGGER.error("createAddress: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (addressRepository.existsById(id)) {
			return true;
		}
		return false;
	}
}
