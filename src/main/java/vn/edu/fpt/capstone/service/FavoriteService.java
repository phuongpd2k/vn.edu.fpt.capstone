package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.FavoriteDto;

public interface FavoriteService {
    FavoriteDto findById(Long id);
    List<FavoriteDto> findAll();
    FavoriteDto updateFavorite(FavoriteDto favoriteDto);
    boolean removeFavorite(Long id);
    FavoriteDto createFavorite(FavoriteDto favoriteDto);
    boolean isExist(Long id);
    FavoriteDto findByUserIdAndPostId(Long userId, Long postId);

}
