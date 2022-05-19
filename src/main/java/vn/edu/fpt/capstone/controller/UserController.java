package vn.edu.fpt.capstone.controller;

import java.util.List;
import java.util.UUID;

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
		ResponseObject response = new ResponseObject();
		try {
			return userService.getUserInformationById(id, jwtToken);
			
		} catch (Exception e) {
			LOGGER.error("Get user information");
			LOGGER.error(e.getMessage());
			response.setCode("1001");
			response.setMessage("Get user info fail!");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/user")
	public ResponseEntity<?> getUserById(
            @RequestHeader(value = "Authorization") String jwtToken) {
		LOGGER.info("Get info user");
		ResponseObject response = new ResponseObject();
		try {
			return userService.getUserInformationByToken(jwtToken.substring(7));
			
		} catch (Exception e) {
			LOGGER.error("Get user information");
			LOGGER.error(e.getMessage());
			response.setCode("1001");
			response.setMessage("Get user info fail!");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PutMapping(value = "/user")
	public ResponseEntity<ResponseObject> putUser(@RequestBody UserDto userDto) {
		ResponseObject response = new ResponseObject();
		try {
			response.setMessage("put request");
			userService.updateUser(userDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("1001");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
			response.setCode("1001");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
