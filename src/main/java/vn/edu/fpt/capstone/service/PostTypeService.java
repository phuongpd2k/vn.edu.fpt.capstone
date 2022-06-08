package vn.edu.fpt.capstone.service;

import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.model.PostTypeModel;

@Service
public interface PostTypeService {
	PostTypeModel getById(Long id);
	boolean isExist(Long id);
}
