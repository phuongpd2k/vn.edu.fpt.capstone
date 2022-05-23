package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.ReportDto;
import vn.edu.fpt.capstone.model.ReportModel;
import vn.edu.fpt.capstone.repository.ReportRepository;
import vn.edu.fpt.capstone.service.ReportService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class ReportServiceImpl implements ReportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class.getName());

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ReportDto findById(Long id) {
        ReportDto reportDto = modelMapper.map(reportRepository.findById(id).get(), ReportDto.class);
        return reportDto;
    }

    @Override
    public List<ReportDto> findAll() {
        List<ReportModel> reportModels = reportRepository.findAll();
        if (reportModels == null || reportModels.isEmpty()) {
            return null;
        }
        List<ReportDto> reportDtos = Arrays.asList(modelMapper.map(reportModels, ReportDto[].class));
        return reportDtos;
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
        if(reportRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
