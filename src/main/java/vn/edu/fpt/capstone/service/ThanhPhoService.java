package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.ThanhPhoDto;

import java.util.List;

public interface ThanhPhoService {
	ThanhPhoDto findById(Long id);
	ThanhPhoDto findBySlug(String slug);
	List<ThanhPhoDto> findAll();
	boolean isExist(Long id);
}
