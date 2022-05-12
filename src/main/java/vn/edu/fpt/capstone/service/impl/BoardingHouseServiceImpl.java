package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.BoardingHouseDto;
import vn.edu.fpt.capstone.model.BoardingHouseModel;
import vn.edu.fpt.capstone.repository.BoardingHouseRepository;
import vn.edu.fpt.capstone.service.BoardingHouseService;

import java.util.Arrays;
import java.util.List;

@Service
public class BoardingHouseServiceImpl implements BoardingHouseService {

    @Autowired
    BoardingHouseRepository boardingHouseRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public BoardingHouseDto findById(Long id) {
        BoardingHouseDto boardingHouseDto = modelMapper.map(boardingHouseRepository.findById(id).get(), BoardingHouseDto.class);
        return boardingHouseDto;
    }

    @Override
    public List<BoardingHouseDto> findAll() {
        List<BoardingHouseModel> boardingHouseModels = boardingHouseRepository.findAll();
        if (boardingHouseModels == null || boardingHouseModels.isEmpty()) {
            return null;
        }
        List<BoardingHouseDto> boardingHouseDtos = Arrays.asList(modelMapper.map(boardingHouseModels, BoardingHouseDto[].class));
        return boardingHouseDtos;
    }

    @Override
    public BoardingHouseDto updateHouse(BoardingHouseDto boardingHouseDto) {
    	BoardingHouseModel boardingHouseModel = modelMapper.map(boardingHouseDto,BoardingHouseModel.class);
        BoardingHouseModel saveModel = boardingHouseRepository.save(boardingHouseModel);
        return modelMapper.map(saveModel, BoardingHouseDto.class);
    }

    @Override
    public boolean removeHouse(Long id) {
    	if(boardingHouseRepository.existsById(id)){
            boardingHouseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public BoardingHouseDto createHouse(BoardingHouseDto boardingHouseDto) {
    	BoardingHouseModel boardingHouseModel = modelMapper.map(boardingHouseDto,BoardingHouseModel.class);
        BoardingHouseModel saveModel = boardingHouseRepository.save(boardingHouseModel);
        return modelMapper.map(saveModel, BoardingHouseDto.class);
    }

    @Override
    public boolean isExist(Long id) {
    	if(boardingHouseRepository.existsById(id)){
            return true;
        }
        return false;
    }


}
