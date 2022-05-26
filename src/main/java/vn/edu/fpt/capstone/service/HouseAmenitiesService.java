package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.HouseAmenitiesDto;

public interface HouseAmenitiesService {
    HouseAmenitiesDto findById(Long id);
    List<HouseAmenitiesDto> findAll();
    HouseAmenitiesDto updateHouseAmenities(HouseAmenitiesDto houseAmenitiesDto);
    boolean removeHouseAmenities(Long id);
    HouseAmenitiesDto createHouseAmenities(HouseAmenitiesDto houseAmenitiesDto);
    boolean isExist(Long id);
    List<HouseAmenitiesDto> createHouseAmenities(List<HouseAmenitiesDto> houseAmenitiesDtos);
}
