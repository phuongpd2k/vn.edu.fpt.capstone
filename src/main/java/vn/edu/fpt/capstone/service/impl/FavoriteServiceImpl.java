package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.FavoriteDto;
import vn.edu.fpt.capstone.model.FavoriteModel;
import vn.edu.fpt.capstone.repository.FavoriteRepository;
import vn.edu.fpt.capstone.service.FavoriteService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteServiceImpl.class.getName());

    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public FavoriteDto findById(Long id) {
        FavoriteDto favoriteDto = modelMapper.map(favoriteRepository.findById(id).get(), FavoriteDto.class);
        return favoriteDto;
    }

    @Override
    public List<FavoriteDto> findAll() {
        List<FavoriteModel> favoriteModels = favoriteRepository.findAll();
        if (favoriteModels == null || favoriteModels.isEmpty()) {
            return null;
        }
        List<FavoriteDto> favoriteDtos = Arrays.asList(modelMapper.map(favoriteModels, FavoriteDto[].class));
        return favoriteDtos;
    }

    @Override
	public FavoriteDto updateFavorite(FavoriteDto favoriteDto) {
		FavoriteModel favoriteModel = modelMapper.map(favoriteDto, FavoriteModel.class);
		FavoriteModel saveModel = favoriteRepository.save(favoriteModel);
		return modelMapper.map(saveModel, FavoriteDto.class);
	}

	@Override
	public boolean removeFavorite(Long id) {
		if (favoriteRepository.existsById(id)) {
			favoriteRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public FavoriteDto createFavorite(FavoriteDto favoriteDto) {
		try {
			FavoriteModel favoriteModel = modelMapper.map(favoriteDto, FavoriteModel.class);
//			if (favoriteModel.getCreatedAt() == null || favoriteModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				favoriteModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				favoriteModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				favoriteModel.setModifiedAt(favoriteModel.getCreatedAt());
//			}
//			if (favoriteModel.getCreatedBy() == null || favoriteModel.getCreatedBy().isEmpty()) {
//				favoriteModel.setCreatedBy("SYSTEM");
//				favoriteModel.setModifiedBy("SYSTEM");
//			} else {
//				favoriteModel.setModifiedBy(favoriteModel.getCreatedBy());
//			}
			FavoriteModel saveModel = favoriteRepository.save(favoriteModel);
			return modelMapper.map(saveModel, FavoriteDto.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
        if(favoriteRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
