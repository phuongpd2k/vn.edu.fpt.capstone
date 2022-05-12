package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.FavoriteDto;
import vn.edu.fpt.capstone.model.FavoriteModel;
import vn.edu.fpt.capstone.repository.FavoriteRepository;
import vn.edu.fpt.capstone.service.FavoriteService;

import java.util.Arrays;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

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
        FavoriteModel favoriteModel = modelMapper.map(favoriteDto,FavoriteModel.class);
        FavoriteModel saveModel = favoriteRepository.save(favoriteModel);
        return modelMapper.map(saveModel,FavoriteDto.class);
    }

    @Override
    public boolean removeFavorite(Long id) {
        if(favoriteRepository.existsById(id)){
            favoriteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public FavoriteDto createFavorite(FavoriteDto favoriteDto) {
        FavoriteModel favoriteModel = modelMapper.map(favoriteDto,FavoriteModel.class);
        FavoriteModel saveModel = favoriteRepository.save(favoriteModel);
        return modelMapper.map(saveModel,FavoriteDto.class);
    }

    @Override
    public boolean isExist(Long id) {
        if(favoriteRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
