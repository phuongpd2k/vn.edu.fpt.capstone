package vn.edu.fpt.capstone.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseHistoryResponse {
	private String type;
	private int price;
	private Date startDate;
	private Date endDate;
	private String status;
}
