package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.ImageDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.ImageService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class.getName());

	@Autowired
	ImageService imageService;

	@GetMapping(value = "/image/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (imageService.isExist(lId)) {
				ImageDto ImageDto = imageService.findById(lId);
				responseObject.setResults(ImageDto);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				LOGGER.info("getById: {}", ImageDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Image is not exist");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}", e);
			responseObject.setCode("404");
			responseObject.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/image")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<ImageDto> ImageDtos = imageService.findAll();
			if (ImageDtos == null || ImageDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(ImageDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessage(Message.OK);
			LOGGER.info("getAll: {}", ImageDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/image")
	public ResponseEntity<ResponseObject> postImage(@RequestBody ImageDto ImageDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (ImageDto.getId() != null) {
				LOGGER.error("postImage: {}", "Wrong body format");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}

			ImageDto imageDto2 = imageService.createImage(ImageDto);
			if (imageDto2 == null) {
				response.setCode("500");
				response.setMessage(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(imageDto2);
			LOGGER.info("postImage: {}", imageDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postImage: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/image")
	public ResponseEntity<ResponseObject> putImage(@RequestBody ImageDto ImageDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (ImageDto.getId() == null || !imageService.isExist(ImageDto.getId())) {
				LOGGER.error("putImage: {}", "ID Image is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			ImageDto imageDto2 = imageService.updateImage(ImageDto);
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(imageDto2);
			LOGGER.info("putImage: {}", imageDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putImage: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/image/{id}")
	public ResponseEntity<ResponseObject> deleteImage(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !imageService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteImage: {}", "ID Image is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			imageService.removeImage(Long.valueOf(id));
			LOGGER.error("deleteImage: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteImage: {}", ex);
			response.setCode("404");
			response.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteImage: {}", e);
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
