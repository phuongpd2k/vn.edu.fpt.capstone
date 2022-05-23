package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.AddressService;
import vn.edu.fpt.capstone.service.HouseService;
import vn.edu.fpt.capstone.service.TypeOfRentalService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HouseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseController.class.getName());

	@Autowired
	HouseService houseService;
	@Autowired
	AddressService addressService;
	@Autowired
	TypeOfRentalService typeOfRentalService;
	@Autowired
	UserService userService;

	@GetMapping(value = "/house/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (houseService.isExist(lId)) {
				HouseDto houseDto = houseService.findById(lId);
				responseObject.setResults(houseDto);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				LOGGER.info("getById: {}",houseDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}","ID House is not exist");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}",e);
			responseObject.setCode("404");
			responseObject.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/house")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<HouseDto> houseDtos = houseService.findAll();
			if (houseDtos == null || houseDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			}else {
				responseObject.setResults(houseDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessage(Message.OK);
			LOGGER.info("getAll: {}",houseDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}",ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/house")
	public ResponseEntity<ResponseObject> postHouse(@RequestBody HouseDto houseDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (houseDto.getId() != null
					|| (houseDto.getUserId() == null || !userService.checkIdExist(houseDto.getUserId()))
					|| (houseDto.getAddressId() == null || !addressService.isExist(houseDto.getAddressId()))
					|| (houseDto.getTypeOfRentalId() == null
							|| !typeOfRentalService.isExist(houseDto.getTypeOfRentalId()))) {
				LOGGER.error("postHouse: {}","Wrong body format or ID User, ID Address, ID Type Of Rental is not exist");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			HouseDto houseDto2 = houseService.createHouse(houseDto);
			if (houseDto2 == null) {
				response.setCode("500");
				response.setMessage(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(houseDto2);
			LOGGER.info("postHouse: {}",houseDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postHouse: {}",e);
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/house")
	public ResponseEntity<ResponseObject> putHouse(@RequestBody HouseDto houseDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (houseDto.getId() == null || !houseService.isExist(houseDto.getId())) {
				LOGGER.error("putHouse: {}","ID House is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if ((houseDto.getUserId() == null || !userService.checkIdExist(houseDto.getUserId()))
					|| (houseDto.getAddressId() == null || !addressService.isExist(houseDto.getAddressId()))
					|| (houseDto.getTypeOfRentalId() == null
							|| !typeOfRentalService.isExist(houseDto.getTypeOfRentalId()))) {
				LOGGER.error("putHouse: {}","ID User, ID Address, ID Type Of Rental is not exist");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			HouseDto houseDto2 = houseService.updateHouse(houseDto);
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(houseDto2);
			LOGGER.info("putHouse: {}",houseDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putHouse: {}",e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/house/{id}")
	public ResponseEntity<ResponseObject> deleteHouse(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !houseService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteHouse: {}","ID House is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			houseService.removeHouse(Long.valueOf(id));
			LOGGER.error("deleteHouse: {}","DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteHouse: {}",ex);
			response.setCode("404");
			response.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteHouse: {}",e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
