package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fpt.capstone.common.Message;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.QuanHuyenService;
import vn.edu.fpt.capstone.service.ThanhPhoService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuanHuyenController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuanHuyenController.class.getName());

    @Autowired
    QuanHuyenService quanHuyenService;
    @Autowired
    ThanhPhoService thanhPhoService;
    @GetMapping(value = "/quanhuyen/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (quanHuyenService.isExist(lId)) {
                QuanHuyenDto quanHuyenDto = quanHuyenService.findById(lId);
                responseObject.setResults(quanHuyenDto);
                responseObject.setCode("200");
                responseObject.setMessage(Message.OK);
                LOGGER.info("getById: {}",quanHuyenDto);
                return new ResponseEntity<>(responseObject, HttpStatus.OK);
            } else {
            	LOGGER.error("getById: {}","ID Quan Huyen is not exist");
                responseObject.setCode("404");
                responseObject.setMessage(Message.NOT_FOUND);
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
        	LOGGER.error("getById: {}",e.toString());
            responseObject.setCode("404");
            responseObject.setMessage(Message.NOT_FOUND);
            return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
        	LOGGER.error("getById: {}",ex.toString());
            responseObject.setCode("500");
            responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    @GetMapping(value = "/quanhuyen")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<QuanHuyenDto> quanHuyenDtos = quanHuyenService.findAll();
            if(quanHuyenDtos==null || quanHuyenDtos.isEmpty()){
            	LOGGER.error("getAll: {}","Data is empty");
                responseObject.setCode("404");
                responseObject.setMessage(Message.NOT_FOUND);
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            LOGGER.info("getAll: {}",quanHuyenDtos);
            responseObject.setResults(quanHuyenDtos);
            responseObject.setCode("200");
            responseObject.setMessage(Message.OK);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception ex) {
        	LOGGER.error("getAll: {}",ex.toString());
            responseObject.setCode("500");
            responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/quanhuyen/thanhpho/{id}")
	public ResponseEntity<ResponseObject> getAllByThanhPhoId(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (thanhPhoService.isExist(lId)) {
				List<QuanHuyenDto> quanHuyenDtos = quanHuyenService.findAllByMaTp(lId);
				responseObject.setResults(quanHuyenDtos);
				responseObject.setCode("200");
				responseObject.setMessage(Message.OK);
				LOGGER.info("getAllByThanhPhoId: {}",quanHuyenDtos);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getAllByThanhPhoId: {}","ID Thanh Pho is not exist");
				responseObject.setCode("404");
				responseObject.setMessage(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getAllByThanhPhoId: {}",e.toString());
			responseObject.setCode("404");
			responseObject.setMessage(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getAllByThanhPhoId: {}",ex.toString());
			responseObject.setCode("500");
			responseObject.setMessage(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
