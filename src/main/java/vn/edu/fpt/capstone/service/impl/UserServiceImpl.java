package vn.edu.fpt.capstone.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.RoleDto;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.RoleModel;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.repository.RoleRepository;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.security.JwtTokenUtil;
import vn.edu.fpt.capstone.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	//private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private UserModel convertToEntity(SignUpDto signUpDto) {
		//UserModel userModel = modelMapper.map(signUpDto, UserModel.class);
		UserModel userModel = new UserModel();
		userModel.setUsername(signUpDto.getUsername());
		userModel.setEmail(signUpDto.getEmail());
		userModel.setPassword(BCrypt.hashpw(signUpDto.getPassword(), BCrypt.gensalt(12)));
		userModel.setFirstName(signUpDto.getFirstName());
		userModel.setLastName(signUpDto.getLastName());
		userModel.setPhoneNumber(signUpDto.getPhoneNumber());
		userModel.setImageLink(signUpDto.getImageLink());
		userModel.setGender(signUpDto.isGender());
		userModel.setDob(signUpDto.getDob());
		userModel.setRole(roleRepository.getRoleByCode(signUpDto.getRole().getRole()));
		
		return userModel;
	}

	private UserDto convertToDto(UserModel userModel) {
		//UserDto userDto = modelMapper.map(userModel, UserDto.class);
		UserDto userDto = new UserDto();
		userDto.setUsername(userModel.getUsername());
		userDto.setEmail(userModel.getEmail());
		userDto.setFirstName(userModel.getFirstName());
		userDto.setLastName(userModel.getLastName());
		userDto.setPhoneNumber(userModel.getPhoneNumber());
		userDto.setImageLink(userModel.getImageLink());
		userDto.setGender(userModel.isGender());
		userDto.setDob(userModel.getDob());
		RoleModel roleModel = roleRepository.getRoleByCode(userModel.getRole().getRole());
		RoleDto roleDto = new RoleDto(roleModel.getId(), roleModel.getRole());	
		userDto.setRole(roleDto);
		return userDto;
	}

	private List<UserDto> convertToListDto(List<UserModel> usersModel) {
		List<UserDto> usersDto = Arrays.asList(modelMapper.map(usersModel, UserDto[].class));
		return usersDto;
	}

	@Override
	public UserModel createUser(SignUpDto signUpDto) {
		return userRepository.save(convertToEntity(signUpDto));
	}

	@Override
	public UserModel updateUser(UserDto userDto) {
		return null;
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
		return userRepository.existsById(id);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public ResponseEntity<?> getUserInformationById(Long id, String jwtToken) {
		ResponseObject response = new ResponseObject();
		
		UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
        	response.setCode("404");
        	response.setMessage("Get user info by id: not found!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        UserDto userDto = convertToDto(user);
        response.setCode("200");
        response.setMessage("Get user info: Successfully!");
        response.setResults(userDto);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
        
	}

	@Override
	public ResponseEntity<?> getUserInformationByToken(String jwtToken) {
		ResponseObject response = new ResponseObject();

		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		System.out.println("aa: " + username);
		UserModel user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
        	response.setCode("404");
        	response.setMessage("Get user info by id: not found!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        UserDto userDto = convertToDto(user);
        response.setCode("200");
        response.setMessage("Get user info: Successfully!");
        response.setResults(userDto);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
	}

}