package vn.edu.fpt.capstone.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.TransactionDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.response.TransactionResponse;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.SearchTransactionDto;
import vn.edu.fpt.capstone.service.TransactionService;
import vn.edu.fpt.capstone.service.UserService;
import vn.edu.fpt.capstone.constant.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class.getName());

	@Autowired
	TransactionService transactionService;
	@Autowired
	UserService userService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private Constant constant;

	@GetMapping(value = "/transaction/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {

		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (transactionService.isExist(lId)) {
				TransactionDto transactionDto = transactionService.findById(lId);
				responseObject.setResults(transactionDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}", transactionDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Transaction is not exist");
				responseObject.setCode("404");
				responseObject.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}", e);
			responseObject.setCode("404");
			responseObject.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/transaction")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<TransactionResponse> transactionDtos = transactionService.findAll();
			if (transactionDtos == null || transactionDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(transactionDtos);
			}
			LOGGER.info("getAll: {}");
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/transaction/user")
	public ResponseEntity<ResponseObject> getAllByToken(@RequestHeader(value = "Authorization") String jwtToken) {
		ResponseObject responseObject = new ResponseObject();
		try {
			UserDto userDto = userService.getUserByToken(jwtToken);
			List<TransactionResponse> transactionDtos = transactionService.findAllByToken(userDto.getId());
			if (transactionDtos == null || transactionDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(transactionDtos);
			}
			LOGGER.info("getAll: {}");
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/transaction")
	public ResponseEntity<ResponseObject> postTransaction(@RequestBody TransactionDto transactionDto,
			@RequestHeader(value = "Authorization") String jwtToken) {
		ResponseObject response = new ResponseObject();
		try {
			UserDto userDto = userService.getUserByToken(jwtToken);

			// Check user ID
			if (userDto == null) {
				LOGGER.error("postTransaction: {}", userDto);
				response.setCode("404");
				response.setMessage("Invalid User");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (transactionDto.getId() != null) {
				LOGGER.error("postTransaction: {}", "Wrong body format");
				response.setCode("406");
				response.setMessage("Wrong body format");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			if (transactionDto.getUser() == null || transactionDto.getUser().getId() == null) {
				LOGGER.error("postTransaction: {}", "ID User is not exist");
				response.setCode("406");
				response.setMessage("ID User is not exist");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				if (transactionDto.getUser().getId() != userDto.getId()) {
					LOGGER.error("postTransaction: {}", userDto);
					response.setCode("404");
					response.setMessage("Invalid User");
					response.setMessageCode(Message.NOT_FOUND);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			}

			// Check transaction type
			if (transactionDto.getTransferType() == null) {
				LOGGER.error("postTransaction: {}", "Invalid transferType");
				response.setCode("406");
				response.setMessage("Invalid transferType");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				switch (transactionDto.getTransferType().toUpperCase()) {
				case "DEPOSIT":
					break;
				case "POSTING":
					break;
				case "POSTING_EXTEND":
					break;
				case "GET_NOTIFICATIONS":
					break;
				case "REFUND":
					break;
				default:
					LOGGER.error("postTransaction: {}", "Invalid transferType");
					response.setCode("406");
					response.setMessage("Invalid transferType");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}

			// Check status
			if (transactionDto.getStatus() == null) {
				LOGGER.error("postTransaction: {}", "Invalid status");
				response.setCode("406");
				response.setMessage("Invalid status");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				switch (transactionDto.getStatus().toUpperCase()) {
				case "SUCCESS":
					break;
				case "FAILED":
					break;
				case "PENDING":
					break;
				case "POSTING_FAILED":
					break;
				case "POSTING_EXTEND_FAILED":
					break;
				case "GET_NOTIFICATIONS_FAILED":
					break;
				default:
					LOGGER.error("postTransaction: {}", "Invalid status");
					response.setCode("406");
					response.setMessage("Invalid status");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}

			// Check action
			if (transactionDto.getAction() == null) {
				LOGGER.error("postTransaction: {}", "Invalid action");
				response.setCode("406");
				response.setMessage("Invalid action");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				switch (transactionDto.getAction().toUpperCase()) {
				case "PLUS":
					break;
				case "MINUS":
					break;
				case "WAITING":
					break;
				case "DO_NOTHING":
					break;
				default:
					LOGGER.error("postTransaction: {}", "Invalid action");
					response.setCode("406");
					response.setMessage("Invalid action");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}
			transactionDto.setLastBalance(userDto.getBalance());
			TransactionDto transactionDto2 = transactionService.createTransaction(transactionDto);
			if (transactionDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(transactionDto2);
			LOGGER.info("postTransaction: {}", transactionDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postTransaction: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/transaction")
	public ResponseEntity<ResponseObject> putTransaction(@RequestBody TransactionDto transactionDto,
			@RequestHeader(value = "Authorization") String jwtToken) {
		ResponseObject response = new ResponseObject();
		try {
			if (transactionDto.getId() == null || !transactionService.isExist(transactionDto.getId())) {
				LOGGER.error("putTransaction: {}", "ID Transaction is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			// Check user ID
			UserDto userDto = userService.getUserByToken(jwtToken);
			if (userDto == null) {
				LOGGER.error("putTransaction: {}", userDto);
				response.setCode("404");
				response.setMessage("Invalid User");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (transactionDto.getUser() == null || transactionDto.getUser().getId() == null) {
				LOGGER.error("putTransaction: {}", "ID User is not exist");
				response.setCode("406");
				response.setMessage("ID User is not exist");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				if (transactionDto.getUser().getId() != userDto.getId()) {
					LOGGER.error("putTransaction: {}", userDto);
					response.setCode("404");
					response.setMessage("Invalid User");
					response.setMessageCode(Message.NOT_FOUND);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			}

			// Check transaction type
			if (transactionDto.getTransferType() == null) {
				LOGGER.error("putTransaction: {}", "Invalid transferType");
				response.setCode("406");
				response.setMessage("Invalid transferType");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				switch (transactionDto.getTransferType().toUpperCase()) {
				case "DEPOSIT":
					break;
				case "POSTING":
					break;
				case "POSTING_EXTEND":
					break;
				case "GET_NOTIFICATIONS":
					break;
				case "REFUND":
					break;
				default:
					LOGGER.error("putTransaction: {}", "Invalid transferType");
					response.setCode("406");
					response.setMessage("Invalid transferType");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}

			// Check status
			if (transactionDto.getStatus() == null) {
				LOGGER.error("putTransaction: {}", "Invalid status");
				response.setCode("406");
				response.setMessage("Invalid status");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				switch (transactionDto.getStatus().toUpperCase()) {
				case "SUCCESS":
					break;
				case "FAILED":
					break;
				case "PENDING":
					break;
				case "POSTING_FAILED":
					break;
				case "POSTING_EXTEND_FAILED":
					break;
				case "GET_NOTIFICATIONS_FAILED":
					break;
				default:
					LOGGER.error("putTransaction: {}", "Invalid status");
					response.setCode("406");
					response.setMessage("Invalid status");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}

			// Check action
			if (transactionDto.getAction() == null) {
				LOGGER.error("putTransaction: {}", "Invalid action");
				response.setCode("406");
				response.setMessage("Invalid action");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				switch (transactionDto.getAction().toUpperCase()) {
				case "PLUS":
					break;
				case "MINUS":
					break;
				case "WAITING":
					break;
				case "DO_NOTHING":
					break;
				default:
					LOGGER.error("putTransaction: {}", "Invalid action");
					response.setCode("406");
					response.setMessage("Invalid action");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}
			TransactionDto transactionDto2 = transactionService.updateTransaction(transactionDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(transactionDto2);
			LOGGER.info("putTransaction: {}", transactionDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putTransaction: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/transaction/{id}")
	public ResponseEntity<ResponseObject> deleteTransaction(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !transactionService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteTransaction: {}", "ID Transaction is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			transactionService.removeTransaction(Long.valueOf(id));
			LOGGER.error("deleteTransaction: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteTransaction: {}", ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteTransaction: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/transaction/deposit")
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public ResponseEntity<ResponseObject> postTransactionDeposit(@RequestBody TransactionDto transactionDto,
			@RequestHeader(value = "Authorization") String jwtToken) {
		ResponseObject response = new ResponseObject();
		try {
			UserDto userDto = userService.getUserByToken(jwtToken);

			// Check user ID
			if (userDto == null) {
				LOGGER.error("postTransaction: {}", userDto);
				response.setCode("404");
				response.setMessage("Invalid User");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (transactionDto.getId() != null) {
				LOGGER.error("postTransaction: {}", "Wrong body format");
				response.setCode("406");
				response.setMessage("Wrong body format");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			transactionDto.setTransferType("DEPOSIT");
			transactionDto.setStatus("PENDING");
			transactionDto.setAction(constant.DO_NOTHING);
			transactionDto.setTransferContent("Nạp tiền");
			transactionDto.setUser(userDto);

			transactionDto.setLastBalance(userDto.getBalance());

			TransactionDto transactionDto2 = transactionService.createTransaction(transactionDto);
			if (transactionDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode("DEPOSIT_SUCCESSFULLY");
			LOGGER.info("postTransaction: {}", transactionDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postTransaction: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/transaction/confirm")
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public ResponseEntity<ResponseObject> confirmTransactionDeposit(@RequestBody TransactionDto transactionDto) {
		ResponseObject response = new ResponseObject();
		try {
			UserDto userDto = userService.getUserById(transactionDto.getUser().getId());

			// Check user ID
			if (userDto == null) {
				LOGGER.error("confirmTransaction: {}", userDto);
				response.setCode("404");
				response.setMessage("Invalid User");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if (transactionDto.getId() == null) {
				LOGGER.error("confirmTransaction: {}", "Wrong body format");
				response.setCode("406");
				response.setMessage("Wrong body format");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			if (transactionDto.getUser() == null || transactionDto.getUser().getId() == null) {
				LOGGER.error("confirmTransaction: {}", "ID User is not exist");
				response.setCode("406");
				response.setMessage("ID User is not exist");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				if (transactionDto.getUser().getId() != userDto.getId()) {
					LOGGER.error("confirmTransaction: {}", userDto);
					response.setCode("404");
					response.setMessage("Invalid User");
					response.setMessageCode(Message.NOT_FOUND);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			}

			userDto.setBalance(userDto.getBalance() + transactionDto.getActualAmount());

			// Update balance in user
			UserModel user = userService.updateUser(userDto);
			if (user == null) {
				LOGGER.error("confirmTransaction: {}", "update user fail");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(ResponseObject.builder().code("500").message("Confirm transaction: update user fail")
								.messageCode("CONFIRM_TRANSACTION_DEPOSIT_FAILED").build());
			}

			TransactionDto dto = transactionService.findById(transactionDto.getId());
			dto.setTransferType("DEPOSIT");
			dto.setStatus("SUCCESS");
			dto.setAction("PLUS");
			dto.setActualAmount(transactionDto.getActualAmount());
			dto.setLastBalance(userDto.getBalance());
			dto.setNote(transactionDto.getNote());
			dto.setDateVerify(new Date());

			TransactionDto transactionDto2 = transactionService.createTransaction(dto);
			if (transactionDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode("CONFIRM_TRANSACTION_DEPOSIT_SUCCESSFULLY");
			LOGGER.info("postTransaction: {}", transactionDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postTransaction: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/transaction/reject")
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public ResponseEntity<ResponseObject> rejectTransactionDeposit(@RequestBody TransactionDto transactionDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (transactionDto.getId() == null) {
				LOGGER.error("confirmTransaction: {}", "Wrong body format");
				response.setCode("406");
				response.setMessage("Wrong body format");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			TransactionDto dto = transactionService.findById(transactionDto.getId());
			dto.setTransferType("DEPOSIT");
			dto.setStatus("FAILED");
			dto.setAction("DO_NOTHING");
			dto.setNote(transactionDto.getNote());

			TransactionDto transactionDto2 = transactionService.createTransaction(dto);
			if (transactionDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode("REJECT_TRANSACTION_SUCCESSFULLY");
			LOGGER.info("postTransaction: {}", transactionDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postTransaction: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/transaction/search")
	public ResponseEntity<?> searchTransaction(@RequestBody SearchTransactionDto search) {
		try {
			List<TransactionResponse> list = transactionService.search(search);

			LOGGER.error("searchTransaction: {}");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.messageCode("SEARCH_TRANSACTION_SUCCESSFULL").results(list).build());
		} catch (Exception e) {
			LOGGER.error("searchTransaction: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Search failed: " + e.getMessage()).messageCode("SEARCH_TRANSACTION_FAILED").build());
		}
	}
	
	@PostMapping(value = "/transaction/search/v2")
	public ResponseEntity<?> searchTransactionV2(@RequestBody SearchTransactionDto search) {
		try {
			List<TransactionResponse> list = transactionService.searchV2(search);

			LOGGER.error("searchTransaction: {}");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.messageCode("SEARCH_TRANSACTION_SUCCESSFULL").results(list).build());
		} catch (Exception e) {
			LOGGER.error("searchTransaction: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Search failed: " + e.getMessage()).messageCode("SEARCH_TRANSACTION_FAILED").build());
		}
	}

	@PostMapping(value = "/transaction/search/post-or-extend")
	public ResponseEntity<?> searchTransactionPostOrExtend(@RequestBody SearchTransactionDto search) {
		try {
			List<TransactionResponse> list = transactionService.searchPostOrExtend(search);

			LOGGER.error("searchTransaction: {}");
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.messageCode("SEARCH_TRANSACTION_SUCCESSFULL").results(list).build());
		} catch (Exception e) {
			LOGGER.error("searchTransaction: {}", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Search failed: " + e.getMessage()).messageCode("SEARCH_TRANSACTION_FAILED").build());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/transaction-by-admin")
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public ResponseEntity<?> postTransactionByAdmin(@RequestBody TransactionDto transactionDto) {
		try {

			// Check user
			if (transactionDto.getUser() == null) {
				LOGGER.error("postTransaction: {}");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create transaction failed: user null").messageCode("USER_NULL").build());
			}

			if (transactionDto.getAmount() <= 0) {
				LOGGER.error("postTransaction: {}");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create transaction failed: amount must be > 0").messageCode("AMOUNT_ERROR").build());
			}

			UserDto userDto = userService.getUserById(transactionDto.getUser().getId());

			if (transactionDto.getAction().equals(constant.PLUS)) {
				userDto.setBalance(userDto.getBalance() + transactionDto.getAmount());
				transactionDto.setTransferContent("Nạp tiền");
			} else if (transactionDto.getAction().equals(constant.MINUS)) {
				if (userDto.getBalance() < transactionDto.getAmount()) {
					LOGGER.error("postTransaction: {}");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(ResponseObject.builder().code("400")
									.message("Create transaction failed: user balance not enough")
									.messageCode("USER_BALANCE_NOT_ENOUGH").build());
				}
				userDto.setBalance(userDto.getBalance() - transactionDto.getAmount());
				transactionDto.setTransferContent("Trừ tiền");
			} else {
				LOGGER.error("postTransaction: {}");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create transaction failed: action error").messageCode("ACTION_ERROR").build());
			}

			// Update balance in user
			UserModel user = userService.updateUser(userDto);
			if (user == null) {
				LOGGER.error("confirmTransaction: {}", "update user fail");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(ResponseObject.builder().code("500").message("Create transaction: update user fail")
								.messageCode("CREATE_TRANSACTION_DEPOSIT_FAILED").build());
			}

			transactionDto.setTransferType(constant.DEPOSIT);
			transactionDto.setStatus(constant.SUCCESS);
			transactionDto.setLastBalance(userDto.getBalance());

			TransactionDto transactionDto2 = transactionService.createTransaction(transactionDto);
			if (transactionDto2 != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(ResponseObject.builder().code("200").message("Create transaction: successfull")
								.messageCode("CREATE_TRANSACTION_DEPOSIT_SUCCESSFULL").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("postTransaction: {}", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("Create transaction: " + e.getMessage())
							.messageCode("CREATE_TRANSACTION_DEPOSIT_FAILED").build());
		}
	}

}
