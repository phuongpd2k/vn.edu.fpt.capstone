package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomImageDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.RoomImageService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomImageController.class.getName());

    @Autowired
    RoomImageService roomImageService;

    @GetMapping(value = "/roomImage/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (roomImageService.isExist(lId)) {
                RoomImageDto roomImageDto = roomImageService.findById(lId);
                responseObject.setResults(roomImageDto);
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

    @GetMapping(value = "/roomImage")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<RoomImageDto> roomImageDtos = roomImageService.findAll();
            if (roomImageDtos == null || roomImageDtos.isEmpty()) {
                responseObject.setCode("404");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(roomImageDtos);
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

    @PostMapping(value = "/roomImage")
    public ResponseEntity<ResponseObject> postRoomImage(@RequestBody RoomImageDto roomImageDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (roomImageDto.getId() != null) {
                response.setMessage("Not Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setMessage("Create successfully");
            roomImageService.createRoomImage(roomImageDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/roomImage")
    public ResponseEntity<ResponseObject> putRoomImage(@RequestBody RoomImageDto roomImageDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (roomImageDto.getId() == null || !roomImageService.isExist(roomImageDto.getId())) {
                response.setCode("404");
                response.setMessage("Not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Update successfully");
            roomImageService.updateRoomImage(roomImageDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/roomImage/{id}")
    public ResponseEntity<ResponseObject> deleteRoomImage(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !roomImageService.isExist(Long.valueOf(id))) {
                response.setCode("404");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Delete successfully");
            roomImageService.removeRoomImage(Long.valueOf(id));
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
