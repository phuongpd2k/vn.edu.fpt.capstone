package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.model.QuanHuyenModel;
import vn.edu.fpt.capstone.repository.QuanHuyenRepository;
import vn.edu.fpt.capstone.service.QuanHuyenService;

import java.util.Arrays;
import java.util.List;


@Service
public class QuanHuyenServiceImpl implements QuanHuyenService {
    @Autowired
    QuanHuyenRepository quanHuyenRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public QuanHuyenDto findById(Long id) {
		QuanHuyenDto quanHuyenDto = modelMapper.map(quanHuyenRepository.findById(id).get(),QuanHuyenDto.class);
		return quanHuyenDto;
    }

    @Override
    @Cacheable("quanHuyen")
    public List<QuanHuyenDto> findAll() {
		List<QuanHuyenModel> quanHuyenModels = quanHuyenRepository.findAll();
		if (quanHuyenModels == null || quanHuyenModels.isEmpty()) {
			return null;
		}
		List<QuanHuyenDto> quanHuyenDtos = Arrays.asList(modelMapper.map(quanHuyenModels, QuanHuyenDto[].class));
		return quanHuyenDtos;
    }

    @Override
    public boolean isExist(Long id) {
        return quanHuyenRepository.existsById(id);
    }

	@Override
	public List<QuanHuyenDto> findAllByMaTp(Long maTp) {
		// TODO Auto-generated method stub
		List<QuanHuyenModel> quanHuyenModels = quanHuyenRepository.findAllByMaTp(maTp);
		if (quanHuyenModels == null || quanHuyenModels.isEmpty()) {
			return null;
		}
		List<QuanHuyenDto> quanHuyenDtos = Arrays.asList(modelMapper.map(quanHuyenModels, QuanHuyenDto[].class));
		return quanHuyenDtos;
	}
}
