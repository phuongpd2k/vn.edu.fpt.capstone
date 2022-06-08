package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.RoomTypeDto;

public interface RoomTypeService {
    RoomTypeDto findById(Long id);
    List<RoomTypeDto> findAll();
    RoomTypeDto updateRoomType(RoomTypeDto roomDto);
    boolean removeRoomType(Long id);
    RoomTypeDto createRoomType(RoomTypeDto roomDto);
    boolean isExist(Long id);
	void removeListRoomType(ListIdDto listIdDto);

}
