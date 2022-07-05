package vn.edu.fpt.capstone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.repository.HouseRepository;
import vn.edu.fpt.capstone.repository.PostRepository;
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.repository.TransactionRepository;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.response.DashBoardAdminResponse;
import vn.edu.fpt.capstone.response.DashBoardHostResponse;
import vn.edu.fpt.capstone.service.DashBoardService;

@Service
public class DashBoardServiceImpl implements DashBoardService{
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private HouseRepository houseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Override
	public DashBoardAdminResponse getDashBoardAdmin() {
		return DashBoardAdminResponse.builder()
				.totalAmount(transactionRepository.getTotalAmountMoney())
				.totalPost(postRepository.getTotalAmountPost())
				.totalHouse(houseRepository.getTotalAmountHouse())
				.totalUser(userRepository.getTotalUser())
				.build();
	}

	@Override
	public DashBoardHostResponse getDashBoardHost(UserDto userDto) {
		return DashBoardHostResponse.builder()
				.totalAmount(transactionRepository.getTotalAmountMoneyHost(userDto.getId()))
				.totalPost(postRepository.getTotalAmountPostHost(userDto.getEmail()))
				.totalHouse(houseRepository.getTotalAmountHouseHost(userDto.getId()))
				.totalRoom(roomRepository.getTotalRoomHost(userDto.getEmail()))
				.build();
	}

}
