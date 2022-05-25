package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.HouseAmenitiesDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.AmenityService;
import vn.edu.fpt.capstone.service.HouseAmenitiesService;
import vn.edu.fpt.capstone.service.HouseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HouseAmenitiesController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseAmenitiesController.class.getName());

	@Autowired
	HouseAmenitiesService houseAmenitiesService;
	@Autowired
	HouseService houseService;
	@Autowired
	AmenityService amenityService;

	@GetMapping(value = "/houseAmenities/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (houseAmenitiesService.isExist(lId)) {
				HouseAmenitiesDto houseAmenitiesDto = houseAmenitiesService.findById(lId);
				responseObject.setResults(houseAmenitiesDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}", houseAmenitiesDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID House Amenities is not exist");
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

	@GetMapping(value = "/houseAmenities")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<HouseAmenitiesDto> houseAmenitiesDtos = houseAmenitiesService.findAll();
			if (houseAmenitiesDtos == null || houseAmenitiesDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(houseAmenitiesDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			LOGGER.info("getAll: {}", houseAmenitiesDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			LOGGER.error(ex.toString());
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/houseAmenities")
	public ResponseEntity<ResponseObject> postHouseAmenities(@RequestBody HouseAmenitiesDto houseAmenitiesDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (houseAmenitiesDto.getId() != null
					|| (houseAmenitiesDto.getAmenityId() == null
							|| !amenityService.isExist(houseAmenitiesDto.getAmenityId()))
					|| (houseAmenitiesDto.getHouseId() == null
							|| !houseService.isExist(houseAmenitiesDto.getHouseId()))) {
				LOGGER.error("postHouseAmenities: {}", "Wrong body format or ID Amenity or ID House is not exist");
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			HouseAmenitiesDto houseAmenitiesDto2 = houseAmenitiesService.createHouseAmenities(houseAmenitiesDto);
			if (houseAmenitiesDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(houseAmenitiesDto2);
			LOGGER.info("postHouseAmenities: {}",houseAmenitiesDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postHouseAmenities: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/houseAmenities")
	public ResponseEntity<ResponseObject> putHouseAmenities(@RequestBody HouseAmenitiesDto houseAmenitiesDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (houseAmenitiesDto.getId() == null || !houseAmenitiesService.isExist(houseAmenitiesDto.getId())) {
				LOGGER.error("putHouseAmenities: {}","ID House Amenities is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if ((houseAmenitiesDto.getAmenityId() == null || !amenityService.isExist(houseAmenitiesDto.getAmenityId()))
					|| (houseAmenitiesDto.getHouseId() == null
							|| !houseService.isExist(houseAmenitiesDto.getHouseId()))) {
				LOGGER.error("putHouseAmenities: {}","ID Amenity or ID House is not exist");
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			HouseAmenitiesDto houseAmenitiesDto2 = houseAmenitiesService.updateHouseAmenities(houseAmenitiesDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(houseAmenitiesDto2);
			LOGGER.info("putHouseAmenities: {}",houseAmenitiesDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putHouseAmenities: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/houseAmenities/{id}")
	public ResponseEntity<ResponseObject> deleteHouseAmenities(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !houseAmenitiesService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteHouseAmenities: {}","ID House Amenities is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			houseAmenitiesService.removeHouseAmenities(Long.valueOf(id));
			LOGGER.error("deleteHouseAmenities: {}","DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteHouseAmenities: {}",ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteHouseAmenities: {}",e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
