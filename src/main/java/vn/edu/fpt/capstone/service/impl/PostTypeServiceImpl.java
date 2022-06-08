package vn.edu.fpt.capstone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.model.PostTypeModel;
import vn.edu.fpt.capstone.repository.PostTypeRepository;
import vn.edu.fpt.capstone.service.PostTypeService;

@Service
public class PostTypeServiceImpl implements PostTypeService{

	@Autowired
	private PostTypeRepository postTypeRepository;
	
	@Override
	public PostTypeModel getById(Long id) {
		return postTypeRepository.getById(id);
	}

	@Override
	public boolean isExist(Long id) {
		return postTypeRepository.existsById(id);
	}

}
