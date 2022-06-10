package vn.edu.fpt.capstone.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.dto.BankAccountDto;
import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.model.BankAccountModel;
import vn.edu.fpt.capstone.service.BankAccountService;

@RestController
@RequestMapping("api/v1/bank-account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BankAccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class.getName());
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping()
	public ResponseEntity<?> getListBankAccount() {
		try {
			List<BankAccountModel> list = bankAccountService.getAllByEnable();
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get list bank account successfully!")
							.messageCode("GET_LIST_BANK_ACCOUNT_SUCCESSFULL").results(Arrays.asList(modelMapper.map(list, BankAccountDto[].class))).build());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("Get list bank account fail!")
							.messageCode("GET_LIST_BANK_ACCOUNT_FAIL").build());
		}
	}

	@PostMapping()
	public ResponseEntity<?> postBankAccount(@RequestBody BankAccountDto bankAccountDto) {
		try {
			if ((bankAccountDto.getBankName().trim().isEmpty())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create bank account: bank name is empty!").messageCode("BANK_NAME_IS_EMPTY").build());
			}
			if ((bankAccountDto.getUsername().trim().isEmpty())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create bank account: user name is empty!").messageCode("USERNAME_IS_EMPTY").build());
			}
			if (bankAccountDto.getNumberAccount() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create bank account: number account is null!").messageCode("NUMBER_ACCOUNT_NULL").build());
			}
			BankAccountModel bankAccountModel = bankAccountService.create(bankAccountDto);

			if (bankAccountModel != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Create bank account: successfully!").messageCode("CREATE_BANK_ACCOUNT_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Create bank account fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create bank account fail: " + e.getMessage()).messageCode("CREATE_BANK_ACCOUNT_FAIL").build());
		}
	}
	
	@PutMapping()
	public ResponseEntity<?> putBankAccount(@RequestBody BankAccountDto bankAccountDto) {
		try {
			if (bankAccountDto.getId() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Update bank account: id is null!").messageCode("ID_NULL").build());
			}
			if ((bankAccountDto.getBankName().trim().isEmpty())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Update bank account: bank name is empty!").messageCode("BANK_NAME_IS_EMPTY").build());
			}
			if ((bankAccountDto.getUsername().trim().isEmpty())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Update bank account: user name is empty!").messageCode("USERNAME_IS_EMPTY").build());
			}
			if (bankAccountDto.getNumberAccount() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Update bank account: number account is null!").messageCode("NUMBER_ACCOUNT_NULL").build());
			}
			BankAccountModel bankAccountModel = bankAccountService.create(bankAccountDto);

			if (bankAccountModel != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Update bank account: successfully!").messageCode("UPDATE_BANK_ACCOUNT_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Update bank account fail: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Update bank account fail: " + e.getMessage()).messageCode("UPDATE_BANK_ACCOUNT_FAIL").build());
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deleteListBankAccount(@RequestBody ListIdDto listIdDto) {
		try {
			bankAccountService.removeListBankAccount(listIdDto);
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Delete list bank account successfully!")
							.messageCode("DELETE_LIST_BANK_ACCOUNT_SUCCESSFULL").build());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("Delete list bank account fail!")
							.messageCode("DELETE_LIST_BANK_ACCOUNT_FAIL").build());
		}
	}
}
