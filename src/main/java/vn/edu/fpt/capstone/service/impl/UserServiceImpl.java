package vn.edu.fpt.capstone.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.RoleDto;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.security.JwtTokenUtil;
import vn.edu.fpt.capstone.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	//private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private UserModel convertToEntity(SignUpDto signUpDto) {
		signUpDto.setPassword(BCrypt.hashpw(signUpDto.getPassword(), BCrypt.gensalt(12)));
		
		return modelMapper.map(signUpDto, UserModel.class);
	}
	
	private UserModel convertToEntity(UserDto userDto) {	
		return modelMapper.map(userDto, UserModel.class);
	}

	private UserDto convertToDto(UserModel userModel) {
		UserDto userDto = modelMapper.map(userModel, UserDto.class);	
		userDto.setRole(new RoleDto(userModel.getRole().getId(), userModel.getRole().getRole()));
		
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
		UserModel user = userRepository.getById(userDto.getId());
		if(user!=null) {
			userDto.setPassword(user.getPassword());
			return userRepository.save(convertToEntity(userDto));
		}
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
		UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder().code("404")
					.message("Get user info by id: not found!").messageCode("GET_USER_INFORMATION_FAIL").build());
        }
        
        UserDto userDto = convertToDto(user);
        
        return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("Get user info: Successfully!").results(userDto).build());
        
	}

	@Override
	public ResponseEntity<?> getUserInformationByToken(String jwtToken) {
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		UserModel user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {      
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder().code("404")
    				.message("Get user info by id: not found!").build());
        }
        
        UserDto userDto = convertToDto(user);
        
        return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("Get user info: Successfully!").results(userDto).build());
	}

	@Override
	public UserModel findByVerificationCode(String code) {
		return userRepository.findByVerificationCode(code).orElse(null);
	}
}