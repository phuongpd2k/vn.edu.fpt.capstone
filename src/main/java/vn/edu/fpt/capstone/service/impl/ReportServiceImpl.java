package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.ReportDto;
import vn.edu.fpt.capstone.model.ReportModel;
import vn.edu.fpt.capstone.repository.ReportRepository;
import vn.edu.fpt.capstone.service.ReportService;

import java.util.Arrays;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

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
        ReportModel reportModel = modelMapper.map(reportDto,ReportModel.class);
        ReportModel saveModel = reportRepository.save(reportModel);
        return modelMapper.map(saveModel,ReportDto.class);
    }

    @Override
    public boolean removeReport(Long id) {
        if(reportRepository.existsById(id)){
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ReportDto createReport(ReportDto reportDto) {
        ReportModel reportModel = modelMapper.map(reportDto,ReportModel.class);
        ReportModel saveModel = reportRepository.save(reportModel);
        return modelMapper.map(saveModel,ReportDto.class);
    }

    @Override
    public boolean isExist(Long id) {
        if(reportRepository.existsById(id)){
            return true;
        }
        return false;
    }
}
