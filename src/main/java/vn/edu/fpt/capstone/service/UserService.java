package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;

public interface UserService {
	UserModel createUser(UserDto userDto);
	UserModel updateUser(UserDto userDto);
	boolean deleteUserById(Long id);
	boolean checkIdExist(Long id);
	List<UserDto> getAllUser();
}
