package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import vn.edu.fpt.capstone.constant.Constant;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.dto.SearchDto;
import vn.edu.fpt.capstone.model.PostModel;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.repository.PostRepository;
import vn.edu.fpt.capstone.repository.PostTypeRepository;
import vn.edu.fpt.capstone.response.PageableResponse;
import vn.edu.fpt.capstone.response.PostResponse;
import vn.edu.fpt.capstone.response.PostingResponse;
import vn.edu.fpt.capstone.service.PostService;
import vn.edu.fpt.capstone.service.QuanHuyenService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.ThanhPhoService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	@Autowired
	private UserService userService;

	@Autowired
	private QuanHuyenService quanHuyenService;

	@Autowired
	private ThanhPhoService thanhPhoService;

	@PersistenceContext
	private EntityManager entityManager;

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
		List<PostModel> postModels = postRepository.findAllQuery();
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
			postResponse.setPostTypeId(model.getPostType().getId());
			postResponse.setPostType(model.getPostType().getType());
			postResponse.setHouseName(model.getHouse().getName());
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
			// set cost
			int costPerDay = postTypeRepository.getById(postDto.getPostType().getId()).getPrice();
			postDto.setCost(postDto.getNumberOfDays() * costPerDay);

			// set end date
			long currentDate = postDto.getStartDate().getTime();
			long addDate = Math.abs((postDto.getNumberOfDays() * TIMESTAMP_DAY));
			Long expiredTime = currentDate + addDate;
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

	@Override
	public List<PostResponse> findAllByToken(String jwtToken) {
		UserModel userModel = userService.getUserInformationByToken(jwtToken);
		List<PostModel> postModels = postRepository.findAllByUsername(userModel.getUsername());
		if (postModels == null || postModels.isEmpty()) {
			return null;
		}
		List<PostResponse> postRes = convertEntity2Response(postModels);
		return postRes;
	}

	@Override
	public PageableResponse findAllPosting(SearchDto searchDto) {
		// If SearchDto equal null then return
		if (searchDto == null) {
			return null;
		}

		// Get page index, page size
		int pageIndex = searchDto.getPageIndex();
		int pageSize = searchDto.getPageSize();
		String key = searchDto.getKeyword();
		
		if(searchDto.getKeyword() == null) {
			key = "";
		}

		// If page index > 0 then reduction 1, else page index = 0
		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}


		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<PostModel> result = postRepository.getListPage(key, pageable);
		List<PostingResponse> listPostingResponse = convertToPostingResponse(result.getContent());
		
		PageableResponse pageableResponse = new PageableResponse();
		pageableResponse.setCurrentPage(pageIndex + 1);
		pageableResponse.setPageSize(pageSize);
		pageableResponse.setTotalPages(result.getTotalPages());
		pageableResponse.setTotalItems(result.getTotalElements());
		pageableResponse.setResults(listPostingResponse);

		return pageableResponse;
	}

	private List<PostingResponse> convertToPostingResponse(List<PostModel> list) {
		List<PostingResponse> listPostingResponse = new ArrayList<PostingResponse>();
		for (PostModel postModel : list) {
			PostingResponse postingResponse = new PostingResponse();
			postingResponse.setNameHouse(postModel.getHouse().getName());
			postingResponse.setNameRoom(postModel.getRoom().getName());
			postingResponse.setImageUrl(postModel.getHouse().getImageUrl());
			postingResponse.setStreet(postModel.getHouse().getAddress().getStreet());
			postingResponse.setPhuongXa(postModel.getHouse().getAddress().getPhuongXa().getName());

			QuanHuyenDto dto = new QuanHuyenDto();
			dto = quanHuyenService.findById(postModel.getHouse().getAddress().getPhuongXa().getMaQh());
			postingResponse.setQuanHuyen(dto.getName());
			postingResponse.setThanhPho(thanhPhoService.findById(dto.getMaTp()).getName());
			Long idHouse = postModel.getHouse().getId();
			postingResponse.setMinPrice(roomService.minPrice(idHouse));
			postingResponse.setMaxPrice(roomService.maxPrice(idHouse));
			postingResponse.setMinArea(roomService.minArea(idHouse));
			postingResponse.setMaxArea(roomService.maxArea(idHouse));
			listPostingResponse.add(postingResponse);
		}
		return listPostingResponse;
	}

}
