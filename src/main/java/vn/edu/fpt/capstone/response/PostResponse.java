package vn.edu.fpt.capstone.response;

import java.util.Date;

import lombok.Data;

@Data
public class PostResponse {
	private Long id;
	private String postType;
	private Date startDate;
	private Date endDate;
	private int cost;
	private int numberOfDays;
	private String status;
}
