package vn.edu.fpt.capstone.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.service.UserService;

public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public boolean createUser(UserDto user) {
		return false;
	}

	@Override
	public boolean updateUser(UserDto user) {
		return false;
	}

	@Override
	public boolean deleteUser(UserDto user) {
		return false;
	}

	@Override
	public List<UserDto> getAllUser() {
		return convertToListDto(repository.findAll());
	}
	
	private UserDto convertToDto (UserModel userModel) {
		UserDto userDto = modelMapper.map(userModel, UserDto.class);
		return userDto;
	}
	private List<UserDto> convertToListDto(List<UserModel> usersModel){
		List<UserDto> usersDto = Arrays.asList(modelMapper.map(usersModel, UserDto[].class));
		return usersDto;
	}

}
