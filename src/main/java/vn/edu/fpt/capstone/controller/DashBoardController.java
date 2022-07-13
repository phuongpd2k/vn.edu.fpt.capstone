package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.response.DBAdminByYearResponse;
import vn.edu.fpt.capstone.response.DBHostByYearResponse;
import vn.edu.fpt.capstone.response.DashBoardAdminResponse;
import vn.edu.fpt.capstone.response.DashBoardHostResponse;
import vn.edu.fpt.capstone.service.DashBoardService;
import vn.edu.fpt.capstone.service.UserService;

@RestController
@RequestMapping("/api/dash-board")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashBoardController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashBoardController.class.getName());
	
	@Autowired
	private DashBoardService dashBoardService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin")
	public ResponseEntity<?> dashBoardAdmin(){
		try {
			DashBoardAdminResponse db = dashBoardService.getDashBoardAdmin();
			LOGGER.error("get dash board: {}");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.messageCode("GET_DASH_BOARD_SUCCESSFULL").results(db).build());
		} catch (Exception e) {
			LOGGER.error("get dash board: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get dash board: " + e.getMessage()).messageCode("GET_DASH_BOARD_FAILED").build());
		}
	}
	
	@GetMapping("/admin/data")
	public ResponseEntity<?> dashBoardAdmin(@RequestParam("year") int year){
		try {
			DBAdminByYearResponse db = dashBoardService.getDashBoardAdminByYear(year);
			LOGGER.error("get dash board: {}");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.messageCode("GET_DASH_BOARD_SUCCESSFULL").results(db).build());
		} catch (Exception e) {
			LOGGER.error("get dash board: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get dash board: " + e.getMessage()).messageCode("GET_DASH_BOARD_FAILED").build());
		}
	}
	
	@GetMapping("/host")
	public ResponseEntity<?> dashBoardHost(@RequestHeader(value = "Authorization") String jwtToken){
		try {
			UserDto userDto = userService.getUserByToken(jwtToken);
			DashBoardHostResponse db = dashBoardService.getDashBoardHost(userDto);
			LOGGER.error("get dash board: {}");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.messageCode("GET_DASH_BOARD_SUCCESSFULL").results(db).build());
		} catch (Exception e) {
			LOGGER.error("get dash board: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get dash board: " + e.getMessage()).messageCode("GET_DASH_BOARD_FAILED").build());
		}
	}
	
	@GetMapping("/host/data")
	public ResponseEntity<?> dashBoardHost(@RequestHeader(value = "Authorization") String jwtToken,
			@RequestParam("year") int year){
		try {
			UserDto userDto = userService.getUserByToken(jwtToken);
			DBHostByYearResponse db = dashBoardService.getDashBoardHostByYear(userDto, year);
			LOGGER.error("get dash board: {}");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.messageCode("GET_DASH_BOARD_SUCCESSFULL").results(db).build());
		} catch (Exception e) {
			LOGGER.error("get dash board: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get dash board: " + e.getMessage()).messageCode("GET_DASH_BOARD_FAILED").build());
		}
	}
}
