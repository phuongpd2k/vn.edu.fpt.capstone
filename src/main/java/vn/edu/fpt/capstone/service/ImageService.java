package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.ImageDto;

public interface ImageService {
    ImageDto findById(Long id);
    List<ImageDto> findAll();
    ImageDto updateImage(ImageDto roomDto);
    boolean removeImage(Long id);
    ImageDto createImage(ImageDto roomDto);
    boolean isExist(Long id);
    ImageDto getImageByUrl(String url);

}
