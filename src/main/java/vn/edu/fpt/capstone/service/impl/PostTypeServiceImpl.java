package vn.edu.fpt.capstone.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.PostTypeDto;
import vn.edu.fpt.capstone.model.PostTypeModel;
import vn.edu.fpt.capstone.repository.PostTypeRepository;
import vn.edu.fpt.capstone.service.PostTypeService;

@Service
public class PostTypeServiceImpl implements PostTypeService{

	@Autowired
	private PostTypeRepository postTypeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PostTypeServiceImpl.class.getName());
	
	@Override
	public PostTypeModel getById(Long id) {
		return postTypeRepository.getById(id);
	}

	@Override
	public boolean isExist(Long id) {
		return postTypeRepository.existsById(id);
	}

	@Override
	public List<PostTypeDto> findAll() {
		List<PostTypeModel> list = postTypeRepository.findAll();
		
		List<PostTypeDto> listDto = Arrays.asList(modelMapper.map(list, PostTypeDto[].class));
		return listDto;
	}

	@Override
	public PostTypeModel create(PostTypeDto postTypeDto) {
		PostTypeModel postTypeModel = modelMapper.map(postTypeDto, PostTypeModel.class);
		return postTypeRepository.save(postTypeModel);
	}

	@Override
	public void removePostType(ListIdDto listIdDto) {
		try {
			for (Long id : listIdDto.getList()) {
				PostTypeModel postTypeModel = postTypeRepository.getById(id);
				if(postTypeModel != null) {
					postTypeModel.setEnable(false);
					postTypeModel = postTypeRepository.save(postTypeModel);
				}
			}
		} catch (Exception e) {
			LOGGER.error("deletePostType: {}", e.getMessage());
		}	
	}

}
