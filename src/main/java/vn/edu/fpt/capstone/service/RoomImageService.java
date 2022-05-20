package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.RoomImageDto;

public interface RoomImageService {
    RoomImageDto findById(Long id);
    List<RoomImageDto> findAll();
    RoomImageDto updateRoomImage(RoomImageDto roomDto);
    boolean removeRoomImage(Long id);
    RoomImageDto createRoomImage(RoomImageDto roomDto);
    boolean isExist(Long id);

}
