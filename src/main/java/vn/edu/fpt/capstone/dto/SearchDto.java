package vn.edu.fpt.capstone.dto;

import lombok.Data;

@Data
public class SearchDto {
	private int pageIndex;
	private int pageSize;
	private String keyword;
	private String text;
	private Long fromDate;
	private Long toDate;
}
