package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.dto.TypeOfRentalDto;
import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.TypeOfRentalService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TypeOfRentalController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TypeOfRentalController.class.getName());

	@Autowired
	TypeOfRentalService typeOfRentalService;
//	@Autowired
//	Cloudinary cloudinaryConfig;

	@GetMapping(value = "/typeOfRental/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (typeOfRentalService.isExist(lId)) {
				TypeOfRentalDto typeOfRentalDto = typeOfRentalService.findById(lId);
				responseObject.setResults(typeOfRentalDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setCode("404");
				responseObject.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error(e.toString());
			responseObject.setCode("404");
			responseObject.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/typeOfRental")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<TypeOfRentalDto> typeOfRentalDtos = typeOfRentalService.findAll();
			if (typeOfRentalDtos == null || typeOfRentalDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			}else {
				responseObject.setResults(typeOfRentalDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/typeOfRental")
	public ResponseEntity<ResponseObject> postTypeOfRental(@RequestBody TypeOfRentalDto typeOfRentalDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (typeOfRentalDto.getId() != null) {
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			TypeOfRentalDto typeOfRentalDto2 = typeOfRentalService.createTypeOfRental(typeOfRentalDto);
			if (typeOfRentalDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(typeOfRentalDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/typeOfRental")
	public ResponseEntity<ResponseObject> putTypeOfRental(@RequestBody TypeOfRentalDto typeOfRentalDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (typeOfRentalDto.getId() == null || !typeOfRentalService.isExist(typeOfRentalDto.getId())) {
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			TypeOfRentalDto typeOfRentalDto2 = typeOfRentalService.updateTypeOfRental(typeOfRentalDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(typeOfRentalDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/typeOfRental/{id}")
	public ResponseEntity<ResponseObject> deleteTypeOfRental(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !typeOfRentalService.isExist(Long.valueOf(id))) {
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			typeOfRentalService.removeTypeOfRental(Long.valueOf(id));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error(ex.toString());
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "/typeOfRental")
	public ResponseEntity<?> deleteListTypeOfRental(@RequestBody ListIdDto listIdDto) {
		try {
			typeOfRentalService.removeListTypeOfRental(listIdDto);
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Delete list type of rental successfully!")
							.messageCode("DELETE_LIST_TYPE_OF_RENTAL_SUCCESSFULL").build());
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("Delete list type of rental fail!")
							.messageCode("DELETE_LIST_TYPE_OF_RENTAL_FAIL").build());
		}
	}

}
