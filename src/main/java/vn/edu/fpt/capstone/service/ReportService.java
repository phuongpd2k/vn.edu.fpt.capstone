package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.ReportDto;

public interface ReportService {
    ReportDto findById(Long id);
    List<ReportDto> findAll();
    ReportDto updateReport(ReportDto reportDto);
    boolean removeReport(Long id);
    ReportDto createReport(ReportDto reportDto);
    boolean isExist(Long id);

}
