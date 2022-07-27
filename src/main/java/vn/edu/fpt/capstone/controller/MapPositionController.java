package vn.edu.fpt.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.dto.MapPositionDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.MapPositionService;

@RestController
@RequestMapping("/api/map-position")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MapPositionController {

	@Autowired
	private MapPositionService mapPositionService;

	@GetMapping
	public ResponseEntity<?> get() {
		try {
			List<MapPositionDto> list = mapPositionService.getAll();
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Get all successfully!").messageCode("GET_ALL_SUCCESSFULLY").results(list).build());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Get all fail!").messageCode("GET_ALL_FAIL").build());
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody MapPositionDto mapPositionDto) {
		try {
			mapPositionService.createOrUpdate(mapPositionDto);
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Create successfully!").messageCode("CREATE_SUCCESSFULLY").build());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Create fail!").messageCode("CREATE_FAIL").build());
		}
	}

	@PutMapping
	public ResponseEntity<?> put(@RequestBody MapPositionDto mapPositionDto) {
		try {
			mapPositionService.createOrUpdate(mapPositionDto);
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Update successfully!").messageCode("UPDATE_SUCCESSFULLY").build());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Update fail!").messageCode("UPDATE_FAIL").build());
		}
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody MapPositionDto mapPositionDto) {
		try {
			mapPositionService.delete(mapPositionDto);
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Delete successfully!").messageCode("DELETE_SUCCESSFULLY").build());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Delete fail!").messageCode("DELETE_FAIL").build());
		}
	}
}
