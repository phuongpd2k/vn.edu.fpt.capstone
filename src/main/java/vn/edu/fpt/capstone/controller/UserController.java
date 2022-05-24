package vn.edu.fpt.capstone.controller;

import java.util.List;

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
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.service.UserService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());
	@Autowired
	private UserService userService;

	// Get user by id

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<?> getUserByIdHasRole(
			@PathVariable long id,
            @RequestHeader(value = "Authorization") String jwtToken) {
		LOGGER.info("Get info id: " + id);
		try {
			return userService.getUserInformationById(id, jwtToken);
			
		} catch (Exception e) {
			LOGGER.error("Get user information");
			LOGGER.error(e.getMessage());
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get user info fail:" + e.getMessage()).messageCode("GET_USER_INFORMATION_FAIL").build());
			
		}
	}
	
//	@CrossOrigin(origins = "*")
	@GetMapping(value = "/user")
	public ResponseEntity<?> getUser(
            @RequestHeader(value = "Authorization") String jwtToken) {
		LOGGER.info("Get info user");
		try {
			return userService.getUserInformationByToken(jwtToken.substring(7));
			
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
			if(userService.updateUser(userDto)!=null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Update user: successfully!").messageCode("UPDATE_USER_SUCCESSFULLY").build());
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("1001")
						.message("Update user: fail!").messageCode("UPDATE_USER_FAIL").build()); 
			}			
			
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("1001")
					.message("Update user: fail!").messageCode("UPDATE_USER_FAIL").build());
		}
	}
	

	@DeleteMapping(value = "/user")
	public ResponseEntity<ResponseObject> deleteUser(@RequestParam(required=true) String id) {
		ResponseObject response = new ResponseObject();
		try {
			response.setMessage("put request");
			userService.deleteUserById(Long.valueOf(id));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Delete user fail:" + e.getMessage()).messageCode("DELETE_USER_FAIL").build());
		}
	}
}
