package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.capstone.dto.PhuongXaDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.ThanhPhoDto;
import vn.edu.fpt.capstone.service.ThanhPhoService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ThanhPhoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThanhPhoController.class.getName());
    @Autowired
    ThanhPhoService thanhPhoService;
    @GetMapping(value = "/thanhpho/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (thanhPhoService.isExist(lId)) {
                ThanhPhoDto thanhPhoDto = thanhPhoService.findById(lId);
                responseObject.setResults(thanhPhoDto);
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

    @GetMapping(value = "/thanhpho")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<ThanhPhoDto> thanhPhoDtos = thanhPhoService.findAll();
            if(thanhPhoDtos==null || thanhPhoDtos.isEmpty()){
                responseObject.setCode("1002");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(thanhPhoDtos);
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
}
