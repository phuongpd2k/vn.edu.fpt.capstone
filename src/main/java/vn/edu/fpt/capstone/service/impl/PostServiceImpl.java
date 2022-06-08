package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.constant.Constant;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.dto.PhuongXaDto;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.RoomDetails;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.dto.ThanhPhoDto;
import vn.edu.fpt.capstone.model.HouseModel;
import vn.edu.fpt.capstone.model.PostModel;
import vn.edu.fpt.capstone.repository.PostRepository;
import vn.edu.fpt.capstone.repository.PostTypeRepository;
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.response.PostResponse;
import vn.edu.fpt.capstone.service.PostService;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class.getName());

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private PostTypeRepository postTypeRepository;
	
	@Autowired
	private Constant constant;
	
	public int TIMESTAMP_DAY = 86400000;

	public List<PostDto> convertEntity2Dto(List<PostModel> models) {
		List<PostDto> postDtos = Arrays.asList(modelMapper.map(models, PostDto[].class));
		for (int i = 0; i < models.size(); i++) {
			Long roomId = postDtos.get(i).getRoom().getId();
			RoomDto roomDto = roomService.findById(roomId);
			postDtos.get(i).setRoom(roomDto);
		}
		return postDtos;
	}

	public PostDto convertEntity2Dto(PostModel model) {
		PostDto postDto = modelMapper.map(model, PostDto.class);
		Long roomId = postDto.getRoom().getId();
		RoomDto roomDto = roomService.findById(roomId);
		postDto.setRoom(roomDto);
		return postDto;
	}

	@Override
	public PostDto findById(Long id) {
		PostDto postDto = convertEntity2Dto(postRepository.findById(id).get());
		return postDto;
	}

	@Override
	public List<PostResponse> findAll() {
		List<PostModel> postModels = postRepository.findAll();
		if (postModels == null || postModels.isEmpty()) {
			return null;
		}
		List<PostResponse> postRes = convertEntity2Response(postModels);
		return postRes;
	}

	private List<PostResponse> convertEntity2Response(List<PostModel> postModels) {
		List<PostResponse> postRes = new ArrayList<PostResponse>();
		for (PostModel model : postModels) {
			PostResponse postResponse = new PostResponse();
			postResponse.setId(model.getId());
			postResponse.setPostType(model.getPostType().getType());
			postResponse.setStartDate(model.getStartDate());
			postResponse.setEndDate(model.getEndDate());
			postResponse.setCost(model.getCost());
			postResponse.setNumberOfDays(model.getNumberOfDays());
			postResponse.setStatus(model.getStatus());
			postRes.add(postResponse);
		}
		return postRes;
	}

	@Override
	public PostDto updatePost(PostDto postDto) {
		PostModel postModel = modelMapper.map(postDto, PostModel.class);
		PostModel saveModel = postRepository.save(postModel);
		return convertEntity2Dto(saveModel);
	}

	@Override
	public boolean removePost(Long id) {
		if (postRepository.existsById(id)) {
			postRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		try {
			//set cost
			int costPerDay = postTypeRepository.getById(postDto.getPostType().getId()).getPrice();
			postDto.setCost(postDto.getNumberOfDays() * costPerDay);
			
			//set end date
			Long expiredTime = postDto.getStartDate().getTime() + (postDto.getNumberOfDays() * TIMESTAMP_DAY);
			postDto.setEndDate(new Date(expiredTime));
			
			PostModel postModel = modelMapper.map(postDto, PostModel.class);
			
			postModel.setStatus(constant.UNCENSORED);
			
			PostModel saveModel = postRepository.save(postModel);
			return convertEntity2Dto(saveModel);
		} catch (Exception e) {
			LOGGER.error("createPost: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (postRepository.existsById(id)) {
			return true;
		}
		return false;
	}

}
