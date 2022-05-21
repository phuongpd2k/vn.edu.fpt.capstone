package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomCategoryDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.RoomCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomCategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomCategoryController.class.getName());

    @Autowired
    RoomCategoryService roomCategoryService;

    @GetMapping(value = "/roomCategory/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (roomCategoryService.isExist(lId)) {
                RoomCategoryDto roomCategoryDto = roomCategoryService.findById(lId);
                responseObject.setResults(roomCategoryDto);
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

    @GetMapping(value = "/roomCategory")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<RoomCategoryDto> roomCategoryDtos = roomCategoryService.findAll();
            if (roomCategoryDtos == null || roomCategoryDtos.isEmpty()) {
                responseObject.setCode("404");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(roomCategoryDtos);
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

    @PostMapping(value = "/roomCategory")
    public ResponseEntity<ResponseObject> postRoomCategory(@RequestBody RoomCategoryDto roomCategoryDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (roomCategoryDto.getId() != null) {
                response.setMessage("Not Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setMessage("Create successfully");
            roomCategoryService.createRoomCategory(roomCategoryDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/roomCategory")
    public ResponseEntity<ResponseObject> putRoomCategory(@RequestBody RoomCategoryDto roomCategoryDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (roomCategoryDto.getId() == null || !roomCategoryService.isExist(roomCategoryDto.getId())) {
                response.setCode("404");
                response.setMessage("Not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Update successfully");
            roomCategoryService.updateRoomCategory(roomCategoryDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/roomCategory/{id}")
    public ResponseEntity<ResponseObject> deleteRoomCategory(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !roomCategoryService.isExist(Long.valueOf(id))) {
                response.setCode("404");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Delete successfully");
            roomCategoryService.removeRoomCategory(Long.valueOf(id));
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
