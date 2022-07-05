package vn.edu.fpt.capstone.dto;

import lombok.Data;

@Data
public class SearchTransactionDto extends SearchDto{
	private String username;
	private String fullName;
	private String status;
	private String type;
}
