package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.BoardingHouseDto;
import vn.edu.fpt.capstone.model.BoardingHouseModel;
import vn.edu.fpt.capstone.repository.BoardingHouseRepository;
import vn.edu.fpt.capstone.service.BoardingHouseService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class BoardingHouseServiceImpl implements BoardingHouseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardingHouseServiceImpl.class.getName());

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
		BoardingHouseModel boardingHouseModel = modelMapper.map(boardingHouseDto, BoardingHouseModel.class);
		BoardingHouseModel saveModel = boardingHouseRepository.save(boardingHouseModel);
		return modelMapper.map(saveModel, BoardingHouseDto.class);
	}

	@Override
	public boolean removeHouse(Long id) {
		if (boardingHouseRepository.existsById(id)) {
			boardingHouseRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public BoardingHouseDto createHouse(BoardingHouseDto boardingHouseDto) {
		try {
			BoardingHouseModel boardingHouseModel = modelMapper.map(boardingHouseDto, BoardingHouseModel.class);
//			if (boardingHouseModel.getCreatedAt() == null || boardingHouseModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				boardingHouseModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				boardingHouseModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				boardingHouseModel.setModifiedAt(boardingHouseModel.getCreatedAt());
//			}
//			if (boardingHouseModel.getCreatedBy() == null || boardingHouseModel.getCreatedBy().isEmpty()) {
//				boardingHouseModel.setCreatedBy("SYSTEM");
//				boardingHouseModel.setModifiedBy("SYSTEM");
//			} else {
//				boardingHouseModel.setModifiedBy(boardingHouseModel.getCreatedBy());
//			}
			BoardingHouseModel saveModel = boardingHouseRepository.save(boardingHouseModel);
			return modelMapper.map(saveModel, BoardingHouseDto.class);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return null;
		}
	}

    @Override
    public boolean isExist(Long id) {
    	if(boardingHouseRepository.existsById(id)){
            return true;
        }
        return false;
    }


}
