package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.ReportDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.dto.request.ReportRequest;
import vn.edu.fpt.capstone.service.PostService;
import vn.edu.fpt.capstone.service.ReportService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.ArrayList;
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
	@Autowired
	PostService postService;

	@GetMapping(value = "/report/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (reportService.isExist(lId)) {
				ReportDto reportDto = reportService.findById(lId);
				responseObject.setResults(reportDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.error("getById: {}", reportDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Report is not exist");
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

	@GetMapping(value = "/report")
	public ResponseEntity<ResponseObject> getAll(@RequestParam(required = false) Long startDate,
			@RequestParam(required = false) Long endDate, @RequestParam(required = false) String fullName,
			@RequestParam(required = false) String userName) {
		ResponseObject responseObject = new ResponseObject();
		try {
			if (startDate == null && endDate == null && fullName == null && userName == null) {
				List<ReportDto> reportDtos = reportService.findAll();
				if (reportDtos == null || reportDtos.isEmpty()) {
					responseObject.setResults(new ArrayList<>());
				} else {
					responseObject.setResults(reportDtos);
				}
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getAll: {}", reportDtos);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			}

			startDate = (startDate == null) ? 0 : startDate;
			endDate = (endDate == null) ? 99999999999999L : endDate;
			LOGGER.info("all Param: \n" + "startDate: {}\n" + "endDate: {}\n" + "fullName: {}\n" + "userName: {}",
					startDate, endDate, fullName, userName);
			List<ReportDto> reportDtos = reportService.searchReports(startDate, endDate, fullName, userName);
			if (reportDtos == null || reportDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(reportDtos);
			}
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			LOGGER.info("getAll: {}", reportDtos);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/report")
	public ResponseEntity<ResponseObject> postReport(@RequestBody ReportRequest reportRequest,
			@RequestHeader(value = "Authorization") String jwtToken) {
		ResponseObject response = new ResponseObject();
		try {
			UserDto userDto = userService.getUserByToken(jwtToken);
			if (userDto == null) {
				response.setCode("406");
				response.setMessage("Wrong body format or User token has expired");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Wrong body format or User token has expired");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			if (reportRequest.getPostId() == null || !postService.isExist(reportRequest.getPostId())) {
				response.setCode("406");
				response.setMessage("Post Id isn't exist");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				LOGGER.error("postReport: {}", "Post Id isn't exist");
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			ReportDto reportDto = reportService.createReport(
					new ReportDto(userDto.getId(), reportRequest.getContent(), new PostDto(reportRequest.getPostId())));
			if (reportDto == null) {
				response.setCode("500");
				response.setMessage("Something wrong when create new report");
				response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			reportDto.setPost(postService.findById(reportRequest.getPostId()));
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(reportDto);
			LOGGER.info("postReport: {}", reportDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("postReport: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
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
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if ((reportDto.getUserId()) == null || !userService.checkIdExist(reportDto.getUserId())) {
				LOGGER.error("putReport: {}", "ID User is not exist");
				response.setCode("406");
				response.setMessageCode(Message.NOT_ACCEPTABLE);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
			ReportDto reportDto2 = reportService.updateReport(reportDto);
			response.setCode("200");
			response.setMessageCode(Message.OK);
			response.setResults(reportDto2);
			LOGGER.info("putReport: {}", reportDto2);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("putReport: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
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
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			reportService.removeReport(Long.valueOf(id));
			LOGGER.info("deleteReport: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deleteReport: {}", ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deleteReport: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
