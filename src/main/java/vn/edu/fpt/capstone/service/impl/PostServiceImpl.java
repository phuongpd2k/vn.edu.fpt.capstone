package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.service.PostService;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.Arrays;
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
	public List<PostDto> findAll() {
		List<PostModel> postModels = postRepository.findAll();
		if (postModels == null || postModels.isEmpty()) {
			return null;
		}
		List<PostDto> postDtos = convertEntity2Dto(postModels);
		return postDtos;
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
			PostModel postModel = modelMapper.map(postDto, PostModel.class);
//			if (postModel.getCreatedAt() == null || postModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				postModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				postModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				postModel.setModifiedAt(postModel.getCreatedAt());
//			}
//			if (postModel.getCreatedBy() == null || postModel.getCreatedBy().isEmpty()) {
//				postModel.setCreatedBy("SYSTEM");
//				postModel.setModifiedBy("SYSTEM");
//			} else {
//				postModel.setModifiedBy(postModel.getCreatedBy());
//			}
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
