package vn.edu.fpt.capstone.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());
	@Autowired
	private UserService userService;

	// Get all user
	@GetMapping(value = "/user")
	public ResponseEntity<ResponseObject> getUser() {
		ResponseObject response = new ResponseObject();
		try {
			response.setCode("1000");
			response.setMessage("Success");
			List<UserDto> listDto = userService.getAllUser();
			response.setResults(listDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("1001");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/user")
	public ResponseEntity<ResponseObject> postUser(@RequestBody UserDto userDto) {
		ResponseObject response = new ResponseObject();
		try {
			response.setMessage("post request");
			userService.createUser(userDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("1001");
			response.setMessage("Failed");
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
