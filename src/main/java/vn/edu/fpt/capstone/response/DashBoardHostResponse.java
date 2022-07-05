package vn.edu.fpt.capstone.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DashBoardHostResponse {
	private float totalAmount;
	private int totalPost;
	private int totalHouse;
	private int totalRoom;
}
