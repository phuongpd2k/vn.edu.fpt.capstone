package vn.edu.fpt.capstone.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import vn.edu.fpt.capstone.dto.RoomDto;

public interface RoomService {
    RoomDto findById(Long id);
    List<RoomDto> findAll();
    //RoomDto updateRoom(RoomDto roomDto);
    boolean removeRoom(Long id);
    //RoomDto createRoom(RoomDto roomDto);
    boolean isExist(Long id);
    
    //v2
    ResponseEntity<?> create(RoomDto roomDto);
    ResponseEntity<?> update(RoomDto roomDto);

}
