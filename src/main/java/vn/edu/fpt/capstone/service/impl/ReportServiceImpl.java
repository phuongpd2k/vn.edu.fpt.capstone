package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ImageDto;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.ReportDto;
import vn.edu.fpt.capstone.model.PostModel;
import vn.edu.fpt.capstone.model.ReportModel;
import vn.edu.fpt.capstone.repository.ReportRepository;
import vn.edu.fpt.capstone.response.PostResponse;
import vn.edu.fpt.capstone.service.QuanHuyenService;
import vn.edu.fpt.capstone.service.ReportService;
import vn.edu.fpt.capstone.service.ThanhPhoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class.getName());

	@Autowired
	ReportRepository reportRepository;
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private QuanHuyenService quanHuyenService;

	@Autowired
	private ThanhPhoService thanhPhoService;

	@Override
	public ReportDto findById(Long id) {
		if (!reportRepository.findById(id).isPresent())
			return null;
		ReportDto reportDto = modelMapper.map(reportRepository.findById(id).get(), ReportDto.class);
		return reportDto;
	}

	@Override
	public List<ReportDto> findAll() {
		List<ReportModel> reportModels = reportRepository.findAll();
		if (reportModels == null || reportModels.isEmpty()) {
			return null;
		}
		// List<ReportDto> reportDtos = Arrays.asList(modelMapper.map(reportModels,
		// ReportDto[].class));
		List<ReportDto> reportDtos = convertToListDto(reportModels);
		return reportDtos;
	}

	private List<ReportDto> convertToListDto(List<ReportModel> reportModels) {
		List<ReportDto> list = new ArrayList<ReportDto>();
		for (ReportModel r : reportModels) {
			ReportDto dto = new ReportDto(r.getUserId(), r.getContent(), convertToPostResponse(r.getPost()));
			dto.setCreatedDate(r.getCreatedDate());
			dto.setCreatedBy(r.getCreatedBy());
			list.add(dto);
		}
		return list;
	}

	private PostResponse convertToPostResponse(PostModel model) {
		PostResponse postResponse = new PostResponse();
		postResponse.setId(model.getId());
		postResponse.setPostTypeId(model.getPostType().getId());
		postResponse.setPostType(model.getPostType().getType());
		postResponse.setRoomName(model.getRoom().getName());
		postResponse.setHouseName(model.getHouse().getName());
		postResponse.setStartDate(model.getStartDate());
		postResponse.setEndDate(model.getEndDate());
		postResponse.setCost(model.getCost());
		postResponse.setNumberOfDays(model.getNumberOfDays());
		postResponse.setStatus(model.getStatus());

		postResponse.setRoomType(model.getRoom().getRoomType().getName());
		postResponse.setRoomCategory(model.getRoom().getRoomCategory().getName());
		postResponse.setArea(model.getHouse().getArea());
		postResponse.setRentalPrice(model.getRoom().getRentalPrice());

		postResponse.setStreet(model.getHouse().getAddress().getStreet());
		postResponse.setPhuongXa(model.getHouse().getAddress().getPhuongXa().getName());
		QuanHuyenDto dto = new QuanHuyenDto();
		dto = quanHuyenService.findById(model.getHouse().getAddress().getPhuongXa().getMaQh());
		postResponse.setQuanHuyen(dto.getName());
		postResponse.setThanhPho(thanhPhoService.findById(dto.getMaTp()).getName());

		postResponse.setHostName(model.getHouse().getUser().getFullName());
		postResponse.setHostPhone(model.getHouse().getUser().getPhoneNumber());

		postResponse.setImages(Arrays.asList(modelMapper.map(model.getRoom().getImages(), ImageDto[].class)));
		postResponse.setVerify(model.getVerify());
		postResponse.setNote(model.getNote());

		postResponse.setPostCode(model.getPost_code());
		postResponse.setUsername(model.getHouse().getUser().getUsername());
		postResponse.setVerifyNote(model.getVerifyNote());
		postResponse.setRoomId(model.getRoom().getId());

		return postResponse;
	}

	@Override
	public ReportDto updateReport(ReportDto reportDto) {
		ReportModel reportModel = modelMapper.map(reportDto, ReportModel.class);
		ReportModel saveModel = reportRepository.save(reportModel);
		return modelMapper.map(saveModel, ReportDto.class);
	}

	@Override
	public boolean removeReport(Long id) {
		if (reportRepository.existsById(id)) {
			reportRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public ReportDto createReport(ReportDto reportDto) {
		try {
			ReportModel reportModel = modelMapper.map(reportDto, ReportModel.class);
//			if (reportModel.getCreatedAt() == null || reportModel.getCreatedAt().toString().isEmpty()) {
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				Date date = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTime();
//				reportModel.setCreatedAt(formatter.parse(formatter.format(date)));
//				reportModel.setModifiedAt(formatter.parse(formatter.format(date)));
//			} else {
//				reportModel.setModifiedAt(reportModel.getCreatedAt());
//			}
//			if (reportModel.getCreatedBy() == null || reportModel.getCreatedBy().isEmpty()) {
//				reportModel.setCreatedBy("SYSTEM");
//				reportModel.setModifiedBy("SYSTEM");
//			} else {
//				reportModel.setModifiedBy(reportModel.getCreatedBy());
//			}
			ReportModel saveModel = reportRepository.save(reportModel);
			return modelMapper.map(saveModel, ReportDto.class);
		} catch (Exception e) {
			LOGGER.error("createReport: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (reportRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	@Override
	public List<ReportDto> searchReports(Long startDate, Long endDate, String fullName, String userName) {
		List<ReportModel> reportModels = reportRepository.searchReports(startDate, endDate, fullName, userName);
		if (reportModels == null || reportModels.isEmpty()) {
			return null;
		}
		List<ReportDto> reportDtos = Arrays.asList(modelMapper.map(reportModels, ReportDto[].class));
		return reportDtos;
	}
}
