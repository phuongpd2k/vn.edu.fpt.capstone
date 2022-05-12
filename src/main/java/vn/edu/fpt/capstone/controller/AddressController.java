package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.AddressDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class.getName());

    @Autowired
    AddressService addressService;

    @GetMapping(value = "/address/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (addressService.isExist(lId)) {
                AddressDto addressDto = addressService.findById(lId);
                responseObject.setResults(addressDto);
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

    @GetMapping(value = "/address")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<AddressDto> addressDtos = addressService.findAll();
            if (addressDtos == null || addressDtos.isEmpty()) {
                responseObject.setCode("1002");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(addressDtos);
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

    @PostMapping(value = "/address")
    public ResponseEntity<ResponseObject> postAddress(@RequestBody AddressDto addressDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (addressDto.getId() != null) {
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setMessage("Create successfully");
            addressService.createAddress(addressDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/address")
    public ResponseEntity<ResponseObject> putAddress(@RequestBody AddressDto addressDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (addressDto.getId() == null || !addressService.isExist(addressDto.getId())) {
                response.setCode("1001");
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setCode("1000");
            response.setMessage("Update successfully");
            addressService.updateAddress(addressDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("1001");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/address/{id}")
    public ResponseEntity<ResponseObject> deleteAddress(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !addressService.isExist(Long.valueOf(id))) {
                response.setCode("1001");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setCode("1000");
            response.setMessage("Delete successfully");
            addressService.removeAddress(Long.valueOf(id));
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
