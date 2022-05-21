package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cloudinary.Cloudinary;

import vn.edu.fpt.capstone.dto.TypeOfRentalDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.TypeOfRentalService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TypeOfRentalController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TypeOfRentalController.class.getName());

	@Autowired
	TypeOfRentalService typeOfRentalService;
	@Autowired
	Cloudinary cloudinaryConfig;

	
	@GetMapping(value = "/typeOfRental/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (typeOfRentalService.isExist(lId)) {
				TypeOfRentalDto typeOfRentalDto = typeOfRentalService.findById(lId);
				responseObject.setResults(typeOfRentalDto);
				responseObject.setCode("200");
				responseObject.setMessage("Successfully");
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				responseObject.setCode("404");
				responseObject.setMessage("Not found");
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error(e.toString());
			responseObject.setCode("404");
			responseObject.setMessage("Not found");
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage("Internal Server Error");
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping(value = "/typeOfRental")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<TypeOfRentalDto> typeOfRentalDtos = typeOfRentalService.findAll();
			if (typeOfRentalDtos == null || typeOfRentalDtos.isEmpty()) {
				responseObject.setCode("404");
				responseObject.setMessage("No data");
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(typeOfRentalDtos);
			responseObject.setCode("200");
			responseObject.setMessage("Successfully");
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage("Internal Server Error");
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PostMapping(value = "/typeOfRental")
	public ResponseEntity<ResponseObject> postTypeOfRental(@RequestBody TypeOfRentalDto typeOfRentalDto) {
		ResponseObject response = new ResponseObject();
		try {
			  if (typeOfRentalDto.getId() != null) {
	                response.setMessage("Invalid data");
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            response.setMessage("Create successfully");
	            typeOfRentalService.createTypeOfRental(typeOfRentalDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	
	@PutMapping(value = "/typeOfRental")
	public ResponseEntity<ResponseObject> putTypeOfRental(@RequestBody TypeOfRentalDto typeOfRentalDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (typeOfRentalDto.getId() == null || !typeOfRentalService.isExist(typeOfRentalDto.getId())) {
				response.setCode("200");
				response.setMessage("Invalid data");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setCode("200");
			response.setMessage("Update successfully");
			typeOfRentalService.updateTypeOfRental(typeOfRentalDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@DeleteMapping(value = "/typeOfRental/{id}")
	public ResponseEntity<ResponseObject> deleteTypeOfRental(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !typeOfRentalService.isExist(Long.valueOf(id))) {
				response.setCode("404");
				response.setMessage("Id is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage("Delete successfully");
			typeOfRentalService.removeTypeOfRental(Long.valueOf(id));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error(ex.toString());
			response.setCode("404");
			response.setMessage("Id is not exist");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
