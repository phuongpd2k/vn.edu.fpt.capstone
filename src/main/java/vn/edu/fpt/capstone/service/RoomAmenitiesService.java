package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.RoomAmenitiesDto;

public interface RoomAmenitiesService {
    RoomAmenitiesDto findById(Long id);
    List<RoomAmenitiesDto> findAll();
    RoomAmenitiesDto updateRoomAmenities(RoomAmenitiesDto roomDto);
    boolean removeRoomAmenities(Long id);
    RoomAmenitiesDto createRoomAmenities(RoomAmenitiesDto roomDto);
    boolean isExist(Long id);

}
