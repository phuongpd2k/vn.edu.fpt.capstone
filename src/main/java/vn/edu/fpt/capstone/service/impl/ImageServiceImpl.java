package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.ImageDto;
import vn.edu.fpt.capstone.model.ImageModel;
import vn.edu.fpt.capstone.repository.ImageRepository;
import vn.edu.fpt.capstone.service.ImageService;

import java.util.Arrays;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class.getName());

    @Autowired
    private ImageRepository ImageRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public ImageDto findById(Long id) {
        ImageDto ImageDto = modelMapper.map(ImageRepository.findById(id).get(), ImageDto.class);
        return ImageDto;
    }

    @Override
    public List<ImageDto> findAll() {
        List<ImageModel> ImageModels = ImageRepository.findAll();
        if (ImageModels == null || ImageModels.isEmpty()) {
            return null;
        }
        List<ImageDto> ImageDtos = Arrays.asList(modelMapper.map(ImageModels, ImageDto[].class));
        return ImageDtos;
    }

    @Override
	public ImageDto updateImage(ImageDto ImageDto) {
		ImageModel ImageModel = modelMapper.map(ImageDto, ImageModel.class);
		ImageModel saveModel = ImageRepository.save(ImageModel);
		return modelMapper.map(saveModel, ImageDto.class);
	}

	@Override
	public boolean removeImage(Long id) {
		if (ImageRepository.existsById(id)) {
			ImageRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public ImageDto createImage(ImageDto ImageDto) {
		try {
			ImageModel ImageModel = modelMapper.map(ImageDto, ImageModel.class);
//			if (ImageModel.getCreatedAt() == null || ImageModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				ImageModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				ImageModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				ImageModel.setModifiedAt(ImageModel.getCreatedAt());
//			}
//			if (ImageModel.getCreatedBy() == null || ImageModel.getCreatedBy().isEmpty()) {
//				ImageModel.setCreatedBy("SYSTEM");
//				ImageModel.setModifiedBy("SYSTEM");
//			} else {
//				ImageModel.setModifiedBy(ImageModel.getCreatedBy());
//			}
			ImageModel saveModel = ImageRepository.save(ImageModel);
			return modelMapper.map(saveModel, ImageDto.class);
		} catch (Exception e) {
			LOGGER.error("createImage: {}", e);
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
        if(ImageRepository.existsById(id)){
            return true;
        }
        return false;
    }

	@Override
	public ImageDto getImageByUrl(String url) {
		ImageModel model = ImageRepository.getImageByUrl(url);
		if(model == null) {
			return null;
		}
		return modelMapper.map(model, ImageDto.class);
	}
}
