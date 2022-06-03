package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.PostDto;

public interface PostService {
	PostDto findById(Long id);

	List<PostDto> findAll();

	PostDto updatePost(PostDto postDto);

	boolean removePost(Long id);

	PostDto createPost(PostDto postDto);

	boolean isExist(Long id);

}
