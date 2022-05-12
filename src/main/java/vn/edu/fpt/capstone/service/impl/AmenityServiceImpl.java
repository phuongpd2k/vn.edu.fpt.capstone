package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.model.AmenityModel;
import vn.edu.fpt.capstone.repository.AmenityRepository;
import vn.edu.fpt.capstone.service.AmenityService;

import java.util.Arrays;
import java.util.List;

@Service
public class AmenityServiceImpl implements AmenityService {
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
        List<AmenityModel> amenityModels = amenityRepository.findAll();
        if (amenityModels == null || amenityModels.isEmpty()) {
            return null;
        }
        List<AmenityDto> amenityDtos = Arrays.asList(modelMapper.map(amenityModels, AmenityDto[].class));
        return amenityDtos;
    }

    @Override
    public AmenityDto updateAmenity(AmenityDto amenityDto) {
        AmenityModel addressModel = modelMapper.map(amenityDto,AmenityModel.class);
        AmenityModel saveModel = amenityRepository.save(addressModel);
        return modelMapper.map(saveModel, AmenityDto.class);
    }

    @Override
    public boolean removeAmenity(Long id) {
        if(amenityRepository.existsById(id)){
            amenityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public AmenityDto createAmenity(AmenityDto amenityDto) {
        AmenityModel amenityModel = modelMapper.map(amenityDto,AmenityModel.class);
        AmenityModel saveModel = amenityRepository.save(amenityModel);
        return modelMapper.map(saveModel, AmenityDto.class);
    }

    @Override
    public boolean isExist(Long id) {
        if(amenityRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
