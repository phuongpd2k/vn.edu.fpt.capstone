package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.RoomModel;
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.service.RoomService;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RoomDto findById(Long id) {
        RoomDto roomDto = modelMapper.map(roomRepository.findById(id).get(), RoomDto.class);
        return roomDto;
    }

    @Override
    public List<RoomDto> findAll() {
        List<RoomModel> roomModels = roomRepository.findAll();
        if (roomModels == null || roomModels.isEmpty()) {
            return null;
        }
        List<RoomDto> roomDtos = Arrays.asList(modelMapper.map(roomModels, RoomDto[].class));
        return roomDtos;
    }

    @Override
    public RoomDto updateRoom(RoomDto roomDto) {
        RoomModel roomModel = modelMapper.map(roomDto,RoomModel.class);
        if(roomModel.getModifiedAt()==null || roomModel.getModifiedAt().toString().isEmpty()){
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            roomModel.setModifiedAt(date);
            roomModel.setModifiedBy("SYSTEM");
        }
        RoomModel saveModel = roomRepository.save(roomModel);
        return modelMapper.map(saveModel,RoomDto.class);
    }

    @Override
    public boolean removeRoom(Long id) {
        if(roomRepository.existsById(id)){
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        RoomModel roomModel = modelMapper.map(roomDto,RoomModel.class);
        if(roomModel.getCreatedAt()==null || roomModel.getCreatedAt().toString().isEmpty()){
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            roomModel.setCreatedAt(date);
            roomModel.setCreatedBy("SYSTEM");
        }
        RoomModel saveModel = roomRepository.save(roomModel);
        return modelMapper.map(saveModel,RoomDto.class);
    }

    @Override
    public boolean isExist(Long id) {
        if(roomRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
