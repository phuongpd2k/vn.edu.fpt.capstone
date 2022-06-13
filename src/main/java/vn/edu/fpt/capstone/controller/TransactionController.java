package vn.edu.fpt.capstone.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.TransactionDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.TransactionModel;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.TransactionService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.ArrayList;
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
			List<TransactionDto> transactionDtos = transactionService.findAll();
			if (transactionDtos == null || transactionDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(transactionDtos);
			}
			LOGGER.info("getAll: {}", transactionDtos);
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
			List<TransactionDto> transactionDtos = transactionService.findAll();
			if (transactionDtos == null || transactionDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(transactionDtos);
			}
			LOGGER.info("getAll: {}", transactionDtos);
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

//	@DeleteMapping(value = "/transaction")
//	public ResponseEntity<?> deleteListTransaction(@RequestBody ListIdDto listIdDto) {
//		try {
//			transactionService.removeListTransaction(listIdDto);
//			return ResponseEntity.status(HttpStatus.OK)
//					.body(ResponseObject.builder().code("200").message("Delete list transaction successfully!")
//							.messageCode("DELETE_LIST_AMENITY_SUCCESSFULL").build());
//		} catch (Exception e) {
//			LOGGER.error(e.toString());
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(ResponseObject.builder().code("400").message("Delete list transaction fail!")
//							.messageCode("DELETE_LIST_AMENITY_FAIL").build());
//		}
//	}

}
