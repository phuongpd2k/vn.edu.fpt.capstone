package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.FilterRoomDto;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.SearchDto;
import vn.edu.fpt.capstone.model.PostModel;
import vn.edu.fpt.capstone.response.PageableResponse;
import vn.edu.fpt.capstone.response.PostResponse;
import vn.edu.fpt.capstone.response.PostingResponse;

public interface PostService {
	PostDto findById(Long id);

	List<PostResponse> findAll();

	PostDto updatePost(PostDto postDto);

	boolean removePost(Long id);

	PostDto createPost(PostDto postDto);

	boolean isExist(Long id);

	List<PostResponse> findAllByToken(String jwtToken);

	PageableResponse findAllPosting(SearchDto searchDto);

	PostModel extendPost(PostDto postDto);

	PostModel confirmPost(PostDto postDto);

	PageableResponse filterPosting(FilterRoomDto dto);

	List<PostingResponse> findTop8Posting();

}
