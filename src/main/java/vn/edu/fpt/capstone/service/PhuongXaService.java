package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.PhuongXaDto;

import java.util.List;

public interface PhuongXaService {
	PhuongXaDto findById (Long id);
	List<PhuongXaDto> findAll();

	boolean isExist(Long id);
}
