package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.QuanHuyenDto;

import java.util.List;

public interface QuanHuyenService {
	QuanHuyenDto findById (Long id);
	List<QuanHuyenDto> findAll();
	boolean isExist(Long id);
}
