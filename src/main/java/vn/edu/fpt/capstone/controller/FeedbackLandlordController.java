package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.FeedbackLandlordDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.FeedbackLandlordService;
import vn.edu.fpt.capstone.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedbackLandlordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackLandlordController.class.getName());

    @Autowired
    FeedbackLandlordService feedbackLandlordService;
    @Autowired
    UserService userService;
    
    @GetMapping(value = "/feedbackLandlord/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (feedbackLandlordService.isExist(lId)) {
                FeedbackLandlordDto feedbackLandlordDto = feedbackLandlordService.findById(lId);
                responseObject.setResults(feedbackLandlordDto);
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
    
    @GetMapping(value = "/feedbackLandlord")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<FeedbackLandlordDto> feedbackLandlordDtos = feedbackLandlordService.findAll();
            if (feedbackLandlordDtos == null || feedbackLandlordDtos.isEmpty()) {
                responseObject.setCode("404");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(feedbackLandlordDtos);
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
    
    @PostMapping(value = "/feedbackLandlord")
    public ResponseEntity<ResponseObject> postFeedbackLandlord(@RequestBody FeedbackLandlordDto feedbackLandlordDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (feedbackLandlordDto.getId() != null) {
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setMessage("Create successfully");
            feedbackLandlordService.createFeedbackLandlord(feedbackLandlordDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value = "/feedbackLandlord")
    public ResponseEntity<ResponseObject> putFeedbackLandlord(@RequestBody FeedbackLandlordDto feedbackLandlordDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (feedbackLandlordDto.getId() == null || !feedbackLandlordService.isExist(feedbackLandlordDto.getId())) {
                response.setCode("404");
                response.setMessage("Invalid data");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Update successfully");
            feedbackLandlordService.updateFeedbackLandlord(feedbackLandlordDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("200");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping(value = "/feedbackLandlord/{id}")
    public ResponseEntity<ResponseObject> deleteFeedbackLandlord(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !feedbackLandlordService.isExist(Long.valueOf(id))) {
                response.setCode("404");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Delete successfully");
            feedbackLandlordService.removeFeedbackLandlord(Long.valueOf(id));
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
