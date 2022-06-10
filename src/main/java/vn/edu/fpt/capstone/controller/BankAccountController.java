package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.dto.BankAccountDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.RoomModel;

@RestController
@RequestMapping("api/v1/bank-account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BankAccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class.getName());

	@PostMapping(value = "/bank-account")
	public ResponseEntity<?> postRoom(@RequestBody BankAccountDto bankAccountDto) {
		try {
			if ((bankAccountDto.getBankName().trim().isEmpty())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create bank account: bank name is empty!").messageCode("BANK_NAME_IS_EMPTY").build());
			}
			if ((bankAccountDto.getUsername().trim().isEmpty())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create bank account: user name is empty!").messageCode("USERNAME_IS_EMPTY").build());
			}
//			if (houseRepository.findById(roomDto.getHouse().getId()) == null) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
//						.message("Create room: id house not exist!").messageCode("ID_HOUSE_NOT_EXIST").build());
//			}
//			RoomModel roomModel = roomService.create(roomDto);

//			if (roomModel != null) {
//				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
//						.message("Create room: successfully!").messageCode("CREATE_ROOM_SUCCESSFULLY").build());
//			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Create room fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create room fail: " + e.getMessage()).messageCode("CREATE_ROOM_FAIL").build());
		}
	}
}
