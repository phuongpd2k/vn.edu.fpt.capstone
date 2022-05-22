package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.common.Message;
import vn.edu.fpt.capstone.dto.ReportDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.ReportService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReportController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class.getName());

	@Autowired
	ReportService reportService;
	@Autowired
	UserService userService;

	@GetMapping(value = "/report/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (reportService.isExist(lId)) {
				ReportDto reportDto = reportService.findById(lId);
				responseObject.setResults(reportDto);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				LOGGER.error("getById: {}", reportDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Report is not exist");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}", e.toString());
			responseObject.setCode("404");
			responseObject.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}", ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/report")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<ReportDto> reportDtos = reportService.findAll();
			if (reportDtos == null || reportDtos.isEmpty()) {
				LOGGER.error("getAll: {}", "Data is empty");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
			responseObject.setResults(reportDtos);
			responseObject.setCode("200");
			responseObject.setMessage(Message.OK);
			LOGGER.info("getAll: {}", reportDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/report")
	public ResponseEntity<ResponseObject> postReport(@RequestBody ReportDto reportDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (reportDto.getId() != null || (reportDto.getUserId()) == null
					|| !userService.checkIdExist(reportDto.getUserId())) {
				LOGGER.error("postReport: {}", "ID User not exist or Wrong body format");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			ReportDto reportDto2 = reportService.createReport(reportDto);
			if (reportDto2 == null) {
				response.setCode("500");
				response.setMessage(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(reportDto2);
			LOGGER.info("postReport: {}", reportDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postReport: {}", e.toString());
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/report")
	public ResponseEntity<ResponseObject> putReport(@RequestBody ReportDto reportDto) {
		ResponseObject response = new ResponseObject();
		try {
			if (reportDto.getId() == null || !reportService.isExist(reportDto.getId())) {
				LOGGER.error("putReport: {}", "ID Report is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if ((reportDto.getUserId()) == null || !userService.checkIdExist(reportDto.getUserId())) {
				LOGGER.error("putReport: {}", "ID User is not exist");
				response.setCode("406");
				response.setMessage(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			ReportDto reportDto2 = reportService.updateReport(reportDto);
			response.setCode("200");
			response.setMessage(Message.OK);
			response.setResults(reportDto2);
			LOGGER.info("putReport: {}", reportDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putReport: {}", e.toString());
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/report/{id}")
	public ResponseEntity<ResponseObject> deleteReport(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !reportService.isExist(Long.valueOf(id))) {
				LOGGER.error("deleteReport: {}", "ID Report is not exist");
				response.setCode("404");
				response.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessage(Message.OK);
			reportService.removeReport(Long.valueOf(id));
			LOGGER.info("deleteReport: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteReport: {}", ex.toString());
			response.setCode("404");
			response.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteReport: {}", e.toString());
			response.setCode("500");
			response.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
