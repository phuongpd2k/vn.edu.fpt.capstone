package vn.edu.fpt.capstone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.MapPositionDto;

@Service
public interface MapPositionService {

	List<MapPositionDto> getAll();

	void createOrUpdate(MapPositionDto mapPositionDto);

	void delete(MapPositionDto mapPositionDto);

}
