package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.RoomCategoryDto;

public interface RoomCategoryService {
    RoomCategoryDto findById(Long id);
    List<RoomCategoryDto> findAll();
    RoomCategoryDto updateRoomCategory(RoomCategoryDto roomDto);
    boolean removeRoomCategory(Long id);
    RoomCategoryDto createRoomCategory(RoomCategoryDto roomDto);
    boolean isExist(Long id);
	void removeListRoomCategory(ListIdDto listIdDto);

}
