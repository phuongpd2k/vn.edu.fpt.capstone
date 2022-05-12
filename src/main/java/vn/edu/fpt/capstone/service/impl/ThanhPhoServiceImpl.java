package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ThanhPhoDto;
import vn.edu.fpt.capstone.model.ThanhPhoModel;
import vn.edu.fpt.capstone.repository.ThanhPhoRepository;
import vn.edu.fpt.capstone.service.ThanhPhoService;

import java.util.Arrays;
import java.util.List;

@Service
public class ThanhPhoServiceImpl implements ThanhPhoService{
	@Autowired
	ThanhPhoRepository thanhPhoRepository;
	@Autowired
	ModelMapper modelMapper;
	@Override
	public ThanhPhoDto findById(Long id) {
		ThanhPhoDto thanhPhoDto = modelMapper.map(thanhPhoRepository.findById(id).get(),ThanhPhoDto.class);
		return thanhPhoDto;
		
	}

	@Override
	public ThanhPhoDto findBySlug(String slug) {
		return thanhPhoRepository.findBySlug(slug);
	}

	@Override
	public List<ThanhPhoDto> findAll() {
		List<ThanhPhoModel> thanhPhoModels = thanhPhoRepository.findAll();
		if (thanhPhoModels == null || thanhPhoModels.isEmpty()) {
			return null;
		}
		List<ThanhPhoDto> thanhPhoDtos = Arrays.asList(modelMapper.map(thanhPhoModels, ThanhPhoDto[].class));
		return thanhPhoDtos;
	}

	@Override
	public boolean isExist(Long id) {
		return thanhPhoRepository.existsById(id);
	}

}
