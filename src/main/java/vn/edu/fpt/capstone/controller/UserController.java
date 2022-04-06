package vn.edu.fpt.capstone.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.UserDto;


@Controller
@RequestMapping("api/user")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());
	@GetMapping(value="/findone")
	public ResponseEntity<ResponseObject> findOne(){
		ResponseObject response = new ResponseObject();
		try {
			UserDto user = new UserDto();
			user.setId(Long.valueOf("1"));
			user.setFirstName("Phương");
			user.setLastName("Phạm");
			user.setEmail("zuyfun@gmail.com");
			user.setPhoneNumber("0914951918");
			user.setDob("01/07/2000");
			user.setGender(true);
			response.setCode("1000");
			response.setMessage("Successful");
			response.setResults(user);
			System.out.println("123");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("1001");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
