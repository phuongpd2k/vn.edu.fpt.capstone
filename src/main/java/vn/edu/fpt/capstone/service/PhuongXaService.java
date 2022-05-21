package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.PhuongXaDto;

import java.util.List;

public interface PhuongXaService {
	PhuongXaDto findById (Long id);
	List<PhuongXaDto> findAll();
	List<PhuongXaDto> findAllByMaQh(Long maQh);
	boolean isExist(Long id);
}
