package vn.edu.fpt.capstone.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper modelMapper;

	private UserModel convertToEntity(UserDto userDtol) {
		UserModel userModel = modelMapper.map(userDtol, UserModel.class);
		return userModel;
	}

	private UserDto convertToDto(UserModel userModel) {
		UserDto userDto = modelMapper.map(userModel, UserDto.class);
		return userDto;
	}

	private List<UserDto> convertToListDto(List<UserModel> usersModel) {
		List<UserDto> usersDto = Arrays.asList(modelMapper.map(usersModel, UserDto[].class));
		return usersDto;
	}

	@Override
	public UserModel createUser(UserDto userDto) {

		return userRepository.save(convertToEntity(userDto));
	}

	@Override
	public UserModel updateUser(UserDto userDto) {
		return userRepository.save(convertToEntity(userDto));
	}

	@Override
	public boolean deleteUserById(Long id) {
		userRepository.deleteById(id);
		if (userRepository.existsById(id)) {
			return false;
		}
		return true;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<UserModel> listModel = userRepository.findAll();
		if (listModel == null || listModel.isEmpty())
			return null;
		return convertToListDto(listModel);
	}

	@Override
	public boolean checkIdExist(Long id) {
		// TODO Auto-generated method stub
		return userRepository.existsById(id);
	}

}
