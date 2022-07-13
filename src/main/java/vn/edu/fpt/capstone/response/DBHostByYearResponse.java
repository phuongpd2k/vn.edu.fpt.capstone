package vn.edu.fpt.capstone.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.capstone.dto.DashBoardDataFloat;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DBHostByYearResponse {
	private List<DashBoardDataFloat> dataDeposit;
	private List<DashBoardDataFloat> dataPostingExtend;
}
