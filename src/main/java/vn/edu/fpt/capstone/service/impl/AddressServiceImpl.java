package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.AddressDto;
import vn.edu.fpt.capstone.model.AddressModel;
import vn.edu.fpt.capstone.repository.AddressRepository;
import vn.edu.fpt.capstone.service.AddressService;

import java.util.Arrays;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public AddressDto findById(Long id) {
        AddressDto addressDto = modelMapper.map(addressRepository.findById(id).get(), AddressDto.class);
        return addressDto;
    }

    @Override
    public List<AddressDto> findAll() {
        List<AddressModel> boardingHouseModels = addressRepository.findAll();
        if (boardingHouseModels == null || boardingHouseModels.isEmpty()) {
            return null;
        }
        List<AddressDto> addressDtos = Arrays.asList(modelMapper.map(boardingHouseModels, AddressDto[].class));
        return addressDtos;
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto) {
        AddressModel addressModel = modelMapper.map(addressDto,AddressModel.class);
        AddressModel saveModel = addressRepository.save(addressModel);
        return modelMapper.map(saveModel,AddressDto.class);
    }

    @Override
    public boolean removeAddress(Long id) {
        if(addressRepository.existsById(id)){
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public AddressDto createAddress(AddressDto addressDto) {
        AddressModel addressModel = modelMapper.map(addressDto,AddressModel.class);
        AddressModel saveModel = addressRepository.save(addressModel);
        return modelMapper.map(saveModel,AddressDto.class);
    }

    @Override
    public boolean isExist(Long id) {
        if(addressRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
