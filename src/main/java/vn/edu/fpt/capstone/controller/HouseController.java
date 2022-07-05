package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.AddressDto;
import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.FilterRoomDto;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.response.HouseHistoryResponse;
import vn.edu.fpt.capstone.service.AddressService;
import vn.edu.fpt.capstone.service.AmenityService;
import vn.edu.fpt.capstone.service.HouseService;
import vn.edu.fpt.capstone.service.PhuongXaService;
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
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	AmenityService amenityService;
	@Autowired
	PhuongXaService phuongXaService;

	@GetMapping(value = "/house/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (houseService.isExist(lId)) {
				HouseDto houseDto = houseService.findById(lId);
				responseObject.setResults(houseDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}", houseDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID House is not exist");
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

	@GetMapping(value = "/house/phuongxa/{id}")
	public ResponseEntity<ResponseObject> getByPhuongXaId(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (phuongXaService.isExist(lId)) {
				List<HouseDto> houseDtos = houseService.findAllByPhuongXaId(lId);
				responseObject.setResults(houseDtos);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getByPhuongXaId: {}", houseDtos);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getByPhuongXaId: {}", "ID Thanh Pho is not exist");
				responseObject.setCode("404");
				responseObject.setMessageCode(Message.NOT_FOUND);
				responseObject.setResults(new ArrayList<HouseDto>());
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getByPhuongXaId: {}", e);
			responseObject.setCode("404");
			responseObject.setResults(new ArrayList<HouseDto>());
			responseObject.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getByPhuongXaId: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
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
			} else {
				responseObject.setResults(houseDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			LOGGER.info("getAll: {}", houseDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER') ")
	@GetMapping(value = "/house/user")
	public ResponseEntity<ResponseObject> getHouseByUser(@RequestHeader(value = "Authorization") String jwtToken) {

		ResponseObject responseObject = new ResponseObject();
		try {
			UserModel userModel = userService.getUserInformationByToken(jwtToken);
			if (userModel == null) {
				LOGGER.error("getHouseByUser: {}", userModel);
				responseObject.setCode("404");
				responseObject.setMessage("Invalid User");
				responseObject.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			List<HouseDto> houseDtos = houseService.findAllByUserId(userModel.getId());
			if (houseDtos == null || houseDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(houseDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			LOGGER.info("getHouseByUser: {}", houseDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getHouseByUser: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/house/user/{id}")
	public ResponseEntity<ResponseObject> getHouseByUserId(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (userService.checkIdExist(lId)) {
				List<HouseDto> houseDtos = houseService.findAllByUserId(lId);
				if (houseDtos == null || houseDtos.isEmpty()) {
					responseObject.setResults(new ArrayList<>());
				} else {
					responseObject.setResults(houseDtos);
				}
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getHouseByUserId: {}", houseDtos);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getHouseByUserId: {}", "ID User is not exist");
				responseObject.setCode("404");
				responseObject.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getHouseByUserId: {}", e);
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

	@PreAuthorize("hasRole('ROLE_LANDLORD')")
	@PostMapping(value = "/house")
	public ResponseEntity<ResponseObject> postHouse(@RequestBody HouseDto houseDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (houseDto.getId() != null || (houseDto.getUser().getId() == null
					|| !userService.checkIdExist((houseDto.getUser().getId()))
					|| (houseDto.getAddress().getId() == null || !addressService.isExist(houseDto.getAddress().getId()))
					|| (houseDto.getTypeOfRental().getId() == null
							|| !typeOfRentalService.isExist(houseDto.getTypeOfRental().getId())))) {
				LOGGER.error("postHouse: {}",
						"Wrong body format or ID User, ID Address, ID Type Of Rental is not exist");
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			HouseDto houseDto2 = houseService.createHouse(houseDto);
			if (houseDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(houseDto2);
			LOGGER.info("postHouse: {}", houseDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postHouse: {}", e);
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_LANDLORD')")
	@PostMapping(value = "/house/create")
	@Transactional
	public ResponseEntity<ResponseObject> postHouseCreate(@RequestBody String jsonString) {
		ResponseObject response = new ResponseObject();
		try {
			HouseDto houseDto = objectMapper.readValue(jsonString, HouseDto.class);
			LOGGER.info("postHouseCreate: {}", houseDto);

			if (houseDto.getName() == null || houseDto.getName().trim().isEmpty()) {
				LOGGER.error("postHouseCreate: {}", "Name can't null or empty");
				response.setCode("406");
				response.setMessage("Name can't null or empty");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			if (houseDto.getDescription() == null || houseDto.getDescription().trim().isEmpty()) {
				LOGGER.error("putHouse: {}", "Description can't null or empty");
				response.setCode("406");
				response.setMessage("Description can't null or empty");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			if (houseDto.getId() != null || (houseDto.getUser() == null || houseDto.getUser().getId() == null
					|| !userService.checkIdExist((houseDto.getUser().getId()))
					|| (houseDto.getTypeOfRental() == null || houseDto.getTypeOfRental().getId() == null
							|| !typeOfRentalService.isExist(houseDto.getTypeOfRental().getId())))) {
				LOGGER.error("postHouseCreate: {}", "Wrong body format or ID User, ID Type Of Rental is not exist");
				response.setCode("406");
				response.setMessage("Wrong body format or ID User, ID Type Of Rental is not exist");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			AddressDto requestAddress = houseDto.getAddress();
			AddressDto responseAddress = new AddressDto();
			if (requestAddress != null && requestAddress.getId() == null) {
				if (requestAddress.getPhuongXa() == null || requestAddress.getPhuongXa().getId() == null
						|| !phuongXaService.isExist(requestAddress.getPhuongXa().getId())) {
					LOGGER.error("postHouseCreate: {}", "ID Phuong Xa is not exist");
					response.setCode("406");
					response.setMessage("ID Phuong Xa is not exist");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
				responseAddress = addressService.createAddress(requestAddress);
				if (responseAddress == null) {
					response.setCode("500");
					response.setMessage("Create new Address failed");
					response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
					return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				LOGGER.error("postHouseCreate: {}", "Wrong body format for Address");
				response.setCode("406");
				response.setMessage("Wrong body format for Address");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			houseDto.setAddress(responseAddress);
			List<AmenityDto> resultAmenity = new ArrayList<AmenityDto>();
			for (AmenityDto amenityDto : houseDto.getAmenities()) {
				if (amenityDto == null || !amenityService.isExist(amenityDto.getId())) {
					LOGGER.error("postHouseCreate: {}", "ID Address is not exist");
					response.setCode("406");
					response.setMessage("ID Amenity is not exist");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				} else {
					for (AmenityDto amenityDto2 : resultAmenity) {
						if (amenityDto.getId() == amenityDto2.getId()) {
							LOGGER.error("postHouseCreate: {}", "ID Address is not exist");
							response.setCode("406");
							response.setMessage("Duplicate ID Amenities");
							response.setMessageCode(Message.NOT_ACCEPTABLE);
							return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
						}
					}
					resultAmenity.add(amenityDto);
				}
			}
			HouseDto houseDto2 = houseService.createHouse(houseDto);
			if (houseDto2 == null) {
				response.setCode("500");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(houseDto2);
			LOGGER.info("postHouseCreate: {}", houseDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (UnrecognizedPropertyException ex) {
			LOGGER.error("postHouseCreate: {}", ex);
			response.setCode("406");
			response.setMessage("Body request is wrong format, please use Json format!");
			response.setMessageCode(Message.NOT_ACCEPTABLE);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			LOGGER.error("postHouseCreate: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_LANDLORD')")
	@PutMapping(value = "/house")
	@Transactional
	public ResponseEntity<ResponseObject> putHouse(@RequestBody HouseDto houseDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (houseDto.getId() == null || !houseService.isExist(houseDto.getId())) {
				LOGGER.error("putHouse: {}", "ID House is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			if (houseDto.getName() == null || houseDto.getName().trim().isEmpty()) {
				LOGGER.error("putHouse: {}", "Name can't null or empty");
				response.setCode("406");
				response.setMessage("Name can't null or empty");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			if (houseDto.getDescription() == null || houseDto.getDescription().trim().isEmpty()) {
				LOGGER.error("putHouse: {}", "Description can't null or empty");
				response.setCode("406");
				response.setMessage("Description can't null or empty");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			if ((houseDto.getUser() == null || houseDto.getUser().getId() == null
					|| !userService.checkIdExist((houseDto.getUser().getId()))
					|| (houseDto.getTypeOfRental() == null || houseDto.getTypeOfRental().getId() == null
							|| !typeOfRentalService.isExist(houseDto.getTypeOfRental().getId())))) {
				LOGGER.error("putHouse: {}", "ID User, ID Type Of Rental is not exist");
				response.setCode("406");
				response.setMessage("ID User, ID Type Of Rental is not exist");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			HouseDto tempHouse = houseService.findById(houseDto.getId());
			if (tempHouse.getUser().getId() != houseDto.getUser().getId()) {
				LOGGER.error("putHouse: {}", "Can't change user");
				response.setCode("406");
				response.setMessage("Can't change user for this house");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			AddressDto requestAddress = houseDto.getAddress();
			AddressDto responseAddress = new AddressDto();
			if (requestAddress != null && (requestAddress.getId() != null)) {
				if (requestAddress.getPhuongXa() == null || requestAddress.getPhuongXa().getId() == null
						|| !phuongXaService.isExist(requestAddress.getPhuongXa().getId())) {
					LOGGER.error("putHouse: {}", "ID Phuong Xa is not exist");
					response.setCode("406");
					response.setMessage("ID Phuong Xa is not exist");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
				if (!addressService.isExist(requestAddress.getId())) {
					LOGGER.error("putHouse: {}", "ID Address is not exist");
					response.setCode("406");
					response.setMessage("ID Address is not exist");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
				responseAddress = addressService.updateAddress(requestAddress);
			} else {
				LOGGER.error("putHouse: {}", "ID Address is not exist");
				response.setCode("406");
				response.setMessage("ID Address is not exist");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			houseDto.setAddress(responseAddress);
			List<AmenityDto> resultAmenity = new ArrayList<AmenityDto>();
			for (AmenityDto amenityDto : houseDto.getAmenities()) {
				if (amenityDto == null || !amenityService.isExist(amenityDto.getId())) {
					LOGGER.error("putHouse: {}", "ID Address is not exist");
					response.setCode("406");
					response.setMessage("ID Amenity is not exist");
					response.setMessageCode(Message.NOT_ACCEPTABLE);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				} else {
					for (AmenityDto amenityDto2 : resultAmenity) {
						if (amenityDto.getId() == amenityDto2.getId()) {
							LOGGER.error("putHouse: {}", "ID Address is not exist");
							response.setCode("406");
							response.setMessage("Duplicate ID Amenities");
							response.setMessageCode(Message.NOT_ACCEPTABLE);
							return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
						}
					}
					resultAmenity.add(amenityDto);
				}
			}
			HouseDto houseDto2 = houseService.updateHouse(houseDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(houseDto2);
			LOGGER.info("putHouse: {}", houseDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putHouse: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/house/{id}")
	public ResponseEntity<ResponseObject> deleteHouse(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !houseService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteHouse: {}", "ID House is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			houseService.removeHouse(Long.valueOf(id));
			LOGGER.error("deleteHouse: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteHouse: {}", ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteHouse: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/house/history")
	public ResponseEntity<ResponseObject> historyHouse(@RequestBody HouseDto houseDto,
			@RequestHeader(value = "Authorization") String jwtToken) {
		try {
			if (houseDto.getId() == null) {
				LOGGER.error("postHouse: {}", "Wrong body format or ID house is not exist");

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Id house null").messageCode("GET_HOUSE_HISTORY_FAIL").build());
			}
			
			UserModel userModel = userService.getUserInformationByToken(jwtToken);
			
			List<HouseHistoryResponse> list = houseService.getListHouseHistory(userModel.getUsername(), houseDto.getId());
			

			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").messageCode("GET_HOUSE_HISTORY_SUCCESSFULL").results(list).build());
		} catch (Exception e) {
			LOGGER.error("postHouse: {}", e);
			LOGGER.error(e.toString());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get house history: " + e.getMessage()).messageCode("INTERNAL_SERVER_ERROR").build());
		}
	}
	
	@PostMapping(value = "/house/filter")
	public ResponseEntity<ResponseObject> historyHouse(@RequestBody FilterRoomDto dto) {
		try {
//			if (houseDto.getId() == null) {
//				LOGGER.error("postHouse: {}", "Wrong body format or ID house is not exist");
//
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
//						.message("Id house null").messageCode("GET_HOUSE_HISTORY_FAIL").build());
//			}
//			
//			
//			List<HouseHistoryResponse> list = houseService.getListHouseHistory(userModel.getUsername(), houseDto.getId());
			

			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").messageCode("GET_HOUSE_HISTORY_SUCCESSFULL").results(null).build());
		} catch (Exception e) {
			LOGGER.error("postHouse: {}", e);
			LOGGER.error(e.toString());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get house history: " + e.getMessage()).messageCode("INTERNAL_SERVER_ERROR").build());
		}
	}

}
