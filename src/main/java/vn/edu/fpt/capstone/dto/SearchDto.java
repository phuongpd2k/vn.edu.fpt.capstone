package vn.edu.fpt.capstone.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SearchDto {
	private int pageIndex;
	private int pageSize;
	private String keyword;
	private String text;
	private Date fromDate;
	private Date toDate;
}
