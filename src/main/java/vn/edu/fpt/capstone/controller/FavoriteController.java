package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.FavoriteDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.FavoriteService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FavoriteController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteController.class.getName());

	@Autowired
	FavoriteService favoriteService;
	@Autowired
	UserService userService;
	@Autowired
	RoomService roomService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/favorite/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (favoriteService.isExist(lId)) {
				FavoriteDto favoriteDto = favoriteService.findById(lId);
				responseObject.setResults(favoriteDto);
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

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/favorite")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<FavoriteDto> favoriteDtos = favoriteService.findAll();
			if (favoriteDtos == null || favoriteDtos.isEmpty()) {
				responseObject.setCode("404");
				responseObject.setMessage("No data");
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(favoriteDtos);
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

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/favorite")
	public ResponseEntity<ResponseObject> postFavorite(@RequestBody FavoriteDto favoriteDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (favoriteDto.getId() != null
					|| (favoriteDto.getUserId() == null || !userService.checkIdExist(favoriteDto.getUserId()))
					|| (favoriteDto.getRoomId() == null || !roomService.isExist(favoriteDto.getRoomId()))) {
				response.setMessage("Invalid data");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage("Create successfully");
			favoriteService.createFavorite(favoriteDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = "/favorite")
	public ResponseEntity<ResponseObject> putFavorite(@RequestBody FavoriteDto favoriteDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (favoriteDto.getId() == null || !favoriteService.isExist(favoriteDto.getId())
					|| (favoriteDto.getUserId() == null || !userService.checkIdExist(favoriteDto.getUserId()))
					|| (favoriteDto.getRoomId() == null || !roomService.isExist(favoriteDto.getRoomId()))) {
				response.setCode("404");
				response.setMessage("Invalid data");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage("Update successfully");
			favoriteService.updateFavorite(favoriteDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			response.setCode("500");
			response.setMessage("Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/favorite/{id}")
	public ResponseEntity<ResponseObject> deleteFavorite(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !favoriteService.isExist(Long.valueOf(id))) {
				response.setCode("404");
				response.setMessage("Id is not exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage("Delete successfully");
			favoriteService.removeFavorite(Long.valueOf(id));
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
