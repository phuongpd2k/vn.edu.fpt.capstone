package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.UserDto;

public interface UserService {
	boolean createUser(UserDto user);
	boolean updateUser(UserDto user);
	boolean deleteUser(UserDto user);
	List<UserDto> getAllUser();
}
