package vn.edu.fpt.capstone.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.dto.UserSearchDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.response.UserListRespone;
import vn.edu.fpt.capstone.service.UserService;
import vn.edu.fpt.capstone.service.impl.UserServiceImpl;
import vn.edu.fpt.capstone.validate.Validation;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());
	@Autowired
	private UserService userService;

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Validation validation;
	
	private String regex_username = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
	private String regex_password = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
	private String regex_email = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/user")
	public ResponseEntity<?> getListUser() {
		LOGGER.info("Get all user info!");
		try {
			List<UserDto> list = userService.getAllUser();
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get all user successfully!")
							.messageCode("GET_ALL_USER_SUCCESSFULLY").results(list).build());
		} catch (Exception e) {
			LOGGER.error("Get all user information");
			LOGGER.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseObject.builder().code("500").message("Get all user info fail:" + e.getMessage())
							.messageCode("GET_ALL_USER_INFORMATION_FAIL").build());

		}
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/user/search")
	public ResponseEntity<?> getListUserSearch(@RequestBody UserSearchDto searchDto) {
		LOGGER.info("Get all user info!");
		try {
			List<UserDto> list = userService.getAllUserSearch(searchDto);
			
			UserListRespone listRespone = new UserListRespone();
			
			int totalUser = userService.getTotalUser();
			listRespone.setTotalAccount(totalUser);
			
			int totalUserActive = userService.getTotalUserActive();	
			listRespone.setAccountActive(totalUserActive);
			listRespone.setAccountInactive(totalUser - totalUserActive);
			listRespone.setData(list);
			
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get user search successfully!")
							.messageCode("GET_USER_SEARCH_SUCCESSFULLY").results(listRespone).build());
		} catch (Exception e) {
			LOGGER.error("Get user search information");
			LOGGER.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseObject.builder().code("500").message("Get user search info fail:" + e.getMessage())
							.messageCode("GET_USER_SEARCH_INFORMATION_FAIL").build());

		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/user/detail/{id}")
	public ResponseEntity<?> getUserByIdHasRole(@PathVariable long id) {
		LOGGER.info("Get info id: " + id);
		try {
			UserModel user = userService.getUserInformationById(id);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder().code("404")
						.message("Get user info by id: not found!").messageCode("GET_USER_INFORMATION_FAIL").build());
			}

			UserDto userDto = userServiceImpl.convertToDto(user);

			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Get user info: Successfully!").results(userDto).build());

		} catch (Exception e) {
			LOGGER.error("Get user information");
			LOGGER.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get user info fail:" + e.getMessage()).messageCode("GET_USER_INFORMATION_FAIL").build());

		}
	}

	@GetMapping(value = "/user/detail")
	public ResponseEntity<?> getUser(@RequestHeader(value = "Authorization") String jwtToken) {
		LOGGER.info("Get info user");
		try {
			UserModel user = userService.getUserInformationByToken(jwtToken);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(ResponseObject.builder().code("404").message("Get user info by id: not found!").build());
			}

			UserDto userDto = userServiceImpl.convertToDto(user);

			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Get user info: Successfully!").results(userDto).build());
		} catch (Exception e) {
			LOGGER.error("Get user information");
			LOGGER.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get user info fail:" + e.getMessage()).messageCode("GET_USER_INFORMATION_FAIL").build());
		}
	}

	@PutMapping(value = "/user")
	public ResponseEntity<?> putUser(@RequestBody UserDto userDto) {
		try {
			UserModel user = userService.getUserInformationById(userDto.getId());
			
			if(userDto.getCccd() != null)
				user.setCccd(userDto.getCccd());
			if(userDto.getEmail() != null)
				user.setEmail(userDto.getEmail());
			if(userDto.getFullName() != null)
				user.setFullName(userDto.getFullName());
			if(userDto.getPhoneNumber() != null)
				user.setPhoneNumber(userDto.getPhoneNumber());
			if(userDto.getUsername() != null) {
				if(!userDto.getUsername().equalsIgnoreCase(user.getUsername())) {
					if(userService.existsByUsername(userDto.getUsername())) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
								.message("Update user: username has exits!").messageCode("UPDATE_USER_FAIL").build());
					}
					user.setUsername(userDto.getUsername());
				}		
			}
				
			
			if(userDto.getDob() != null) {
				user.setDob(userDto.getDob());
			}
			
			user.setGender(userDto.isGender());
			
			UserModel user2 = userService.updateUser(modelMapper.map(user, UserDto.class));
			if (user2 != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Update user: successfully!").messageCode("UPDATE_USER_SUCCESSFULLY").build());
			}
			throw new Exception();

		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("1001")
					.message("Update user: fail!").messageCode("UPDATE_USER_FAIL").build());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/user/lock")
	public ResponseEntity<?> lockUser(@RequestParam(required = true) Long id) {
		try {
			userService.lockUserById(id);

			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Lock user successfully!").messageCode("LOCK_USER_SUCCESSFULLY").build());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Lock user fail:" + e.getMessage()).messageCode("LOCK_USER_FAIL").build());
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/user/unlock")
	public ResponseEntity<?> unLockUser(@RequestParam(required = true) Long id) {
		try {
			userService.unLockUserById(id);

			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("unlock user successfully!").messageCode("UNLOCK_USER_SUCCESSFULLY").build());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Unlock user fail:" + e.getMessage()).messageCode("UNLOCK_USER_FAIL").build());
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/user")
	public ResponseEntity<?> deleteUser(@RequestParam(required = true) Long id) {
		try {
			userService.deleteUserById(id);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("200")
					.message("Delete user successfully!").messageCode("DELETE_USER_SUCCESSFULLY").build());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Delete user fail:" + e.getMessage()).messageCode("DELETE_USER_FAIL").build());
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/user")
	public ResponseEntity<?> postUser(@RequestBody SignUpDto signUpDto) {
		try {
			if(signUpDto == null) {
				throw new Exception();
			}
			if(userService.existsByUsername(signUpDto.getUsername())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create user: username has exits!").messageCode("CREATE_USER_FAIL").build());
			}
			// Validate email
			if (!validation.checkRegex(regex_email, signUpDto.getEmail()) || signUpDto.getEmail().isEmpty()) {
				LOGGER.error("Email invalid!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create user by admin: email invalid!").messageCode("EMAIL_INVALID").build());
			}

			// Validate user name
			// user name is 8-20 characters long
			// no _ or . at the beginning
			// no __ or _. or ._ or .. inside
			// allowed characters
			// no _ or . at the end
			if (!validation.checkRegex(regex_username, signUpDto.getUsername()) || signUpDto.getUsername().isEmpty()) {
				LOGGER.error("Username invalid!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create user by admin: username invalid!").messageCode("USERNAME_INVALID").build());
			}

			// Validate password
			// Minimum eight characters, at least one letter and one number
			if (!validation.checkRegex(regex_password, signUpDto.getPassword()) || signUpDto.getPassword().isEmpty()) {
				LOGGER.error("Email invalid!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create user by admin: password invalid!").messageCode("PASSWORD_INVALID").build());
			}

			// Check for User name exists in a DB
			if (userService.existsByUsername(signUpDto.getUsername())) {
				LOGGER.error("User name has exits!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create user by admin: user name has exist!").messageCode("USERNAME_HAS_EXISTED").build());
			}

			// Check for email exists in a DB
			if (userService.existsByEmail(signUpDto.getEmail())) {
				LOGGER.error("Email has exits!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create user by admin: Email has exits!").messageCode("EMAIL_HAS_EXISTED").build());
			}
			
			UserModel user2 = userService.createByAdmin(signUpDto);
			if (user2 != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Create user by admin: successfully!").messageCode("CREATE_USER_SUCCESSFULLY").build());
			}
			throw new Exception();

		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("1001")
					.message("Create user by admin: " + e.getMessage()).messageCode("UPDATE_USER_FAIL").build());
		}
	}

	@PutMapping(value = "/user/update-role")
	public ResponseEntity<?> putUserUpdateRole(@RequestBody UserDto userDto,
			@RequestHeader(value = "Authorization") String jwtToken) {
		try {
			UserModel user = userService.userUpdateRole(userDto, jwtToken);
			if (user != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(ResponseObject.builder().code("200").message("Update role user: successfully!")
								.messageCode("UPDATE_ROLE_USER_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("1001")
					.message("Update role user: " + e.getMessage()).messageCode("UPDATE_ROLE_USER_FAIL").build());
		}
	}
	
	@PutMapping(value = "/user/update-image")
	public ResponseEntity<?> putUserUpdateImage(@RequestBody UserDto userDto,
			@RequestHeader(value = "Authorization") String jwtToken) {
		try {
			UserDto user = userService.getUserById(userDto.getId());
			user.setImageLink(userDto.getImageLink());
			
			UserModel userModel = userService.updateUser(user);
			if (userModel != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(ResponseObject.builder().code("200").message("Update image user: successfully!")
								.messageCode("UPDATE_IMAGE_USER_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Update image user: " + e.getMessage()).messageCode("UPDATE_IMAGE_USER_FAIL").build());
		}
	}
}
