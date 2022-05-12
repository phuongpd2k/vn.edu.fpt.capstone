package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.RoomRentalHistoryDto;

public interface RoomRentalHistoryService {
    RoomRentalHistoryDto findById(Long id);
    List<RoomRentalHistoryDto> findAll();
    RoomRentalHistoryDto updateRoomRentalHistory(RoomRentalHistoryDto roomRentalHistoryDto);
    boolean removeRoomRentalHistory(Long id);
    RoomRentalHistoryDto createRoomRentalHistory(RoomRentalHistoryDto roomRentalHistoryDto);
    boolean isExist(Long id);

}
