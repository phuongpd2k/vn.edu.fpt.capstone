package vn.edu.fpt.capstone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.PostTypeDto;
import vn.edu.fpt.capstone.model.PostTypeModel;

@Service
public interface PostTypeService {
	PostTypeModel getById(Long id);
	boolean isExist(Long id);
	List<PostTypeDto> findAll();
	PostTypeModel create(PostTypeDto postTypeDto);
	void removePostType(ListIdDto listIdDto);
}
