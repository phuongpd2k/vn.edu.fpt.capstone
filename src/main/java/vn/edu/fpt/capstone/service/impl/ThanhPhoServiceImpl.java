package vn.edu.fpt.capstone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.repository.ThanhPhoRepository;
import vn.edu.fpt.capstone.service.ThanhPhoService;

@Service
public class ThanhPhoServiceImpl implements ThanhPhoService{
	@Autowired
	ThanhPhoRepository thanhPhoRepository;
	
	@Override
	public void findById(Long id) {
		thanhPhoRepository.findById(id);
		
	}

	@Override
	public void findBySlug(String slug) {
		thanhPhoRepository.findBySlug(slug);
	}

	@Override
	public void findAll() {
		thanhPhoRepository.findAll();
	}

}
