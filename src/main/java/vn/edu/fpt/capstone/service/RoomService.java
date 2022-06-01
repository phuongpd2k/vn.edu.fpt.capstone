package vn.edu.fpt.capstone.service;

import java.util.List;
import org.springframework.data.domain.Page;

import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.RoomModel;

public interface RoomService {
    RoomDto findById(Long id);
    List<RoomDto> findAll();
    boolean removeRoom(Long id);
    boolean isExist(Long id);
    RoomModel create(RoomDto roomDto);
    Page<RoomModel> getPage(int pageSize, int pageIndex, Long houseId);
	void deleteRoom(Long id);
}
