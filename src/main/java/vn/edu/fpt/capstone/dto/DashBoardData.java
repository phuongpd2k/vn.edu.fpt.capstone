package vn.edu.fpt.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DashBoardData {
	private int month;
	private Long number;
}
