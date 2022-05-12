package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.BoardingHouseDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.BoardingHouseService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardingHouseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoardingHouseController.class.getName());

    @Autowired
    BoardingHouseService boardingHouseService;

    @GetMapping(value = "/boardingHouse/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (boardingHouseService.isExist(lId)) {
                BoardingHouseDto boardingHouseDto = boardingHouseService.findById(lId);
                responseObject.setResults(boardingHouseDto);
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

    @GetMapping(value = "/boardingHouse")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<BoardingHouseDto> boardingHouseDtos = boardingHouseService.findAll();
            if (boardingHouseDtos == null || boardingHouseDtos.isEmpty()) {
                responseObject.setCode("1002");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(boardingHouseDtos);
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

    @PostMapping(value = "/boardingHouse")
    public ResponseEntity<ResponseObject> postBoardingHouse(@RequestBody BoardingHouseDto boardingHouseDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (boardingHouseDto.getId() != null) {
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setMessage("Create successfully");
            boardingHouseService.createHouse(boardingHouseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/boardingHouse")
    public ResponseEntity<ResponseObject> putBoardingHouse(@RequestBody BoardingHouseDto boardingHouseDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (boardingHouseDto.getId() == null || !boardingHouseService.isExist(boardingHouseDto.getId())) {
                response.setCode("1001");
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setCode("1000");
            response.setMessage("Update successfully");
            boardingHouseService.updateHouse(boardingHouseDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/boardingHouse/{id}")
    public ResponseEntity<ResponseObject> deleteBoardingHouse(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !boardingHouseService.isExist(Long.valueOf(id))) {
                response.setCode("1001");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setCode("1000");
            response.setMessage("Delete successfully");
            boardingHouseService.removeHouse(Long.valueOf(id));
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
