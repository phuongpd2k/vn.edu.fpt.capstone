package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.BoardingHouseDto;

import java.util.List;

public interface BoardingHouseService {
    BoardingHouseDto findById(Long id);
    List<BoardingHouseDto> findAll();
    BoardingHouseDto updateHouse(BoardingHouseDto boardingHouseDto);
    boolean removeHouse(Long id);
    BoardingHouseDto createHouse(BoardingHouseDto boardingHouseDto);
    boolean isExist(Long id);

}
