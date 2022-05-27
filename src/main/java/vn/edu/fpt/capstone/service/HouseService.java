package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.HouseDto;

import java.util.List;

public interface HouseService {
	HouseDto findById(Long id);

	List<HouseDto> findAllByPhuongXaId(Long xaId);

	List<HouseDto> findAll();

	HouseDto updateHouse(HouseDto houseDto);

	boolean removeHouse(Long id);

	HouseDto createHouse(HouseDto houseDto);

	boolean isExist(Long id);

}
