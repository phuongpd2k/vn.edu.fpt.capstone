package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.SearchDto;
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

	List<PostingResponse> findAllPosting(SearchDto searchDto);

}
