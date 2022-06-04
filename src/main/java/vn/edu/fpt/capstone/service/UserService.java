package vn.edu.fpt.capstone.service;

import java.util.List;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.SearchDto;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;

@Service
public interface UserService {
	UserModel createUser(SignUpDto signUpDto);
	UserModel updateUser(UserDto userDto);
	boolean checkIdExist(Long id);
	List<UserDto> getAllUser();
	boolean existsByUsername(String username);
	void deleteUserById(Long id);
	UserModel getUserInformationById(Long id);
	UserModel getUserInformationByToken(String jwtToken);
	UserModel findByVerificationCode(String code);
	UserModel userUpdateRole(UserDto userDto, String jwtToken);
	List<UserDto> getAllUserSearch(SearchDto searchDto);
	int countUserActive();
}
