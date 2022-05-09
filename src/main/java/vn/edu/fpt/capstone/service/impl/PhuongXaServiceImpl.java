package vn.edu.fpt.capstone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.repository.PhuongXaRepository;
import vn.edu.fpt.capstone.service.PhuongXaService;

@Service
public class PhuongXaServiceImpl implements PhuongXaService{
	@Autowired
	PhuongXaRepository phuongXaRepository;
	@Override
	public void findById(Long id) {
		phuongXaRepository.findById(id);
		
	}

	@Override
	public void findAll() {
		phuongXaRepository.findAll();
	}

}
