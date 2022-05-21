package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.dto.ImageDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class.getName());

    @Autowired
    ImageService ImageService;

    @GetMapping(value = "/Image/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Long lId = Long.valueOf(id);
            if (ImageService.isExist(lId)) {
                ImageDto ImageDto = ImageService.findById(lId);
                responseObject.setResults(ImageDto);
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

    @GetMapping(value = "/Image")
    public ResponseEntity<ResponseObject> getAll() {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<ImageDto> ImageDtos = ImageService.findAll();
            if (ImageDtos == null || ImageDtos.isEmpty()) {
                responseObject.setCode("404");
                responseObject.setMessage("No data");
                return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
            }
            responseObject.setResults(ImageDtos);
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

    @PostMapping(value = "/Image")
    public ResponseEntity<ResponseObject> postImage(@RequestBody ImageDto ImageDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (ImageDto.getId() != null) {
                response.setMessage("Not Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setMessage("Create successfully");
            ImageService.createImage(ImageDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/Image")
    public ResponseEntity<ResponseObject> putImage(@RequestBody ImageDto ImageDto) {
        ResponseObject response = new ResponseObject();
        try {
            if (ImageDto.getId() == null || !ImageService.isExist(ImageDto.getId())) {
                response.setCode("404");
                response.setMessage("Not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Update successfully");
            ImageService.updateImage(ImageDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            response.setCode("500");
            response.setMessage("Failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/Image/{id}")
    public ResponseEntity<ResponseObject> deleteImage(@PathVariable String id) {
        ResponseObject response = new ResponseObject();
        try {
            if (id == null || id.isEmpty() || !ImageService.isExist(Long.valueOf(id))) {
                response.setCode("404");
                response.setMessage("Id is not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setCode("200");
            response.setMessage("Delete successfully");
            ImageService.removeImage(Long.valueOf(id));
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
