package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class.getName());

    @Autowired
    RoomService roomService;

    @GetMapping(value = "/room/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (roomService.isExist(lId)) {
                RoomDto roomDto = roomService.findById(lId);
                responseObject.setResults(roomDto);
                responseObject.setCode("1001");
                responseObject.setMessage("Successfully");
                return new ResponseEntity<>(responseObject, HttpStatus.OK);
            } else {
                responseObject.setCode("1002");
                responseObject.setMessage("Not found");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            LOGGER.error(e.toString());
            responseObject.setCode("1002");
            responseObject.setMessage("Not found");
            return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
            responseObject.setCode("1003");
            responseObject.setMessage("Internal Server Error");
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/room")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<RoomDto> roomDtos = roomService.findAll();
            if (roomDtos == null || roomDtos.isEmpty()) {
                responseObject.setCode("1002");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(roomDtos);
            responseObject.setCode("1001");
            responseObject.setMessage("Successfully");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
            responseObject.setCode("1003");
            responseObject.setMessage("Internal Server Error");
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/room")
    public ResponseEntity<ResponseObject> postRoom(@RequestBody RoomDto roomDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (roomDto.getId() != null) {
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setMessage("Create successfully");
            roomService.createRoom(roomDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/room")
    public ResponseEntity<ResponseObject> putRoom(@RequestBody RoomDto roomDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (roomDto.getId() == null || !roomService.isExist(roomDto.getId())) {
                response.setCode("1001");
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setCode("1000");
            response.setMessage("Update successfully");
            roomService.updateRoom(roomDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/room/{id}")
    public ResponseEntity<ResponseObject> deleteRoom(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !roomService.isExist(Long.valueOf(id))) {
                response.setCode("1001");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setCode("1000");
            response.setMessage("Delete successfully");
            roomService.removeRoom(Long.valueOf(id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            LOGGER.error(ex.toString());
            response.setCode("1001");
            response.setMessage("Id is not exist");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
