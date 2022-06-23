package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.dto.PhuongXaDto;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.RoomDetails;
import vn.edu.fpt.capstone.dto.ThanhPhoDto;
import vn.edu.fpt.capstone.model.HouseModel;
import vn.edu.fpt.capstone.repository.HouseRepository;
import vn.edu.fpt.capstone.repository.PostRepository;
import vn.edu.fpt.capstone.response.HouseHistoryResponse;
import vn.edu.fpt.capstone.service.HouseService;
import vn.edu.fpt.capstone.service.PhuongXaService;
import vn.edu.fpt.capstone.service.QuanHuyenService;
import vn.edu.fpt.capstone.service.ThanhPhoService;
import java.util.Arrays;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseServiceImpl.class.getName());

	@Autowired
	HouseRepository houseRepository;
	@Autowired
	QuanHuyenService quanHuyenService;
	@Autowired
	ThanhPhoService thanhPhoService;
	@Autowired
	PhuongXaService phuongXaService;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	private PostRepository postRepository;

	public List<HouseDto> convertEntity2Dto(List<HouseModel> models) {
		List<HouseDto> houseDtos = Arrays.asList(modelMapper.map(models, HouseDto[].class));
		for (int i = 0; i < houseDtos.size(); i++) {
			if (houseDtos.get(i).getAddress() != null || houseDtos.get(i).getAddress().getId() != null) {
				if (houseDtos.get(i).getAddress().getPhuongXa() != null) {
					if (houseDtos.get(i).getAddress().getPhuongXa().getId() != null) {
						PhuongXaDto phuongXaDto = phuongXaService
								.findById(houseDtos.get(i).getAddress().getPhuongXa().getId());
						Long maQh = phuongXaDto.getMaQh();
						if (maQh != null) {
							QuanHuyenDto quanHuyenDto = quanHuyenService.findById(phuongXaDto.getMaQh());
							ThanhPhoDto thanhPhoDto = thanhPhoService.findById(quanHuyenDto.getMaTp());
							quanHuyenDto.setThanhPho(thanhPhoDto);
							phuongXaDto.setQuanHuyen(quanHuyenDto);
							houseDtos.get(i).getAddress().setPhuongXa(phuongXaDto);
						}
					}

				}
			}
			houseDtos.get(i).setRoomDetails(new RoomDetails());
			houseDtos.get(i).getRoomDetails()
					.setRoomCount(houseRepository.countRoomByHouseId(houseDtos.get(i).getId()));
		}
		return houseDtos;
	}

	public HouseDto convertEntity2Dto(HouseModel model) {
		HouseDto houseDto = modelMapper.map(model, HouseDto.class);
		if (houseDto.getAddress() != null || houseDto.getAddress().getId() != null) {
			if (houseDto.getAddress().getPhuongXa() != null) {
				if (houseDto.getAddress().getPhuongXa().getId() != null) {
					PhuongXaDto phuongXaDto = phuongXaService.findById(houseDto.getAddress().getPhuongXa().getId());
					Long maQh = phuongXaDto.getMaQh();
					if (maQh != null) {
						QuanHuyenDto quanHuyenDto = quanHuyenService.findById(phuongXaDto.getMaQh());
						ThanhPhoDto thanhPhoDto = thanhPhoService.findById(quanHuyenDto.getMaTp());
						quanHuyenDto.setThanhPho(thanhPhoDto);
						phuongXaDto.setQuanHuyen(quanHuyenDto);
						houseDto.getAddress().setPhuongXa(phuongXaDto);
					}
				}

			}

		}
		houseDto.setRoomDetails(new RoomDetails());
		houseDto.getRoomDetails().setRoomCount(houseRepository.countRoomByHouseId(houseDto.getId()));
		return houseDto;
	}

	@Override
	public HouseDto findById(Long id) {
		HouseDto houseDto = convertEntity2Dto(houseRepository.findById(id).get());
		return houseDto;
	}

	@Override
	public List<HouseDto> findAll() {
		List<HouseModel> houseModels = houseRepository.findAll();
		if (houseModels == null || houseModels.isEmpty()) {
			return null;
		}
		List<HouseDto> houseDtos = convertEntity2Dto(houseModels);
		return houseDtos;
	}

	@Override
	public HouseDto updateHouse(HouseDto houseDto) {
		HouseModel houseModel = modelMapper.map(houseDto, HouseModel.class);
		HouseModel saveModel = houseRepository.save(houseModel);
		return convertEntity2Dto(saveModel);
	}

	@Override
	public boolean removeHouse(Long id) {
		if (houseRepository.existsById(id)) {
			HouseModel model = houseRepository.getById(id);
			model.setEnable(false);
			houseRepository.save(model);
			return true;
		}
		return false;
	}

	@Override
	public HouseDto createHouse(HouseDto houseDto) {
		try {
			HouseModel houseModel = modelMapper.map(houseDto, HouseModel.class);
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
			HouseModel saveModel = houseRepository.saveAndFlush(houseModel);
			return convertEntity2Dto(saveModel);
		} catch (Exception e) {
			LOGGER.error("createHouse: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (houseRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	@Override
	public List<HouseDto> findAllByPhuongXaId(Long xaId) {
		List<HouseModel> houseModels = houseRepository.findByXaId(xaId);
		if (houseModels == null || houseModels.isEmpty()) {
			return null;
		}
		List<HouseDto> houseDtos = convertEntity2Dto(houseModels);
		return houseDtos;
	}

	@Override
	public List<HouseDto> findAllByUserId(Long userId) {
		List<HouseModel> houseModels = houseRepository.findByUserId(userId);
		if (houseModels == null || houseModels.isEmpty()) {
			return null;
		}
		List<HouseDto> houseDtos = convertEntity2Dto(houseModels);
		return houseDtos;
	}

	@Override
	public List<HouseHistoryResponse> getListHouseHistory(String username, Long id) {
		return postRepository.getListHouseHistory(username, id);
	}

}
