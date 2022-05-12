package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto findById(Long id);
    List<AddressDto> findAll();
    AddressDto updateAddress(AddressDto addressDto);
    boolean removeAddress(Long id);
    AddressDto createAddress(AddressDto addressDto);
    boolean isExist(Long id);
}
