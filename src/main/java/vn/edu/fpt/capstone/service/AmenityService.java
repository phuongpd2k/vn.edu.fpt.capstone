package vn.edu.fpt.capstone.service;
import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.ListIdDto;

import java.util.List;

public interface AmenityService {
	AmenityDto findById(Long id);

	List<AmenityDto> findAll();

	AmenityDto updateAmenity(AmenityDto amenityDto);

	boolean removeAmenity(Long id);

	AmenityDto createAmenity(AmenityDto amenityDto);

	boolean isExist(Long id);

	void removeListAmenity(ListIdDto listIdDto);

}
