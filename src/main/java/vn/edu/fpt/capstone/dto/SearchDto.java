package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SearchDto {
	private int pageIndex;
	private int pageSize;
	private String keyword;
	private String text;
	private Long fromDate;
	private Long toDate;
    private String fromDateStr;
    
    private String toDateStr;
}
