package vn.edu.fpt.capstone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.repository.QuanHuyenRepository;
import vn.edu.fpt.capstone.service.QuanHuyenService;

@Service
public class QuanHuyenServiceImpl implements QuanHuyenService {
	@Autowired
	QuanHuyenRepository quanHuyenRepository;

	@Override
	public void findById(Long id) {
		quanHuyenRepository.findById(id);

	}

	@Override
	public void findAll() {
		quanHuyenRepository.findAll();
	}

}
