package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomTypeDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.RoomTypeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomTypeController.class.getName());

    @Autowired
    RoomTypeService roomTypeService;

    @GetMapping(value = "/roomType/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (roomTypeService.isExist(lId)) {
                RoomTypeDto roomTypeDto = roomTypeService.findById(lId);
                responseObject.setResults(roomTypeDto);
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

    @GetMapping(value = "/roomType")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<RoomTypeDto> roomTypeDtos = roomTypeService.findAll();
            if (roomTypeDtos == null || roomTypeDtos.isEmpty()) {
                responseObject.setCode("404");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(roomTypeDtos);
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

    @PostMapping(value = "/roomType")
    public ResponseEntity<ResponseObject> postRoomType(@RequestBody RoomTypeDto roomTypeDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (roomTypeDto.getId() != null) {
                response.setMessage("Not Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setMessage("Create successfully");
            roomTypeService.createRoomType(roomTypeDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/roomType")
    public ResponseEntity<ResponseObject> putRoomType(@RequestBody RoomTypeDto roomTypeDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (roomTypeDto.getId() == null || !roomTypeService.isExist(roomTypeDto.getId())) {
                response.setCode("404");
                response.setMessage("Not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Update successfully");
            roomTypeService.updateRoomType(roomTypeDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/roomType/{id}")
    public ResponseEntity<ResponseObject> deleteRoomType(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !roomTypeService.isExist(Long.valueOf(id))) {
                response.setCode("404");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Delete successfully");
            roomTypeService.removeRoomType(Long.valueOf(id));
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
