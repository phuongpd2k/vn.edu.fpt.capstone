package vn.edu.fpt.capstone.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponse {
	private int currentPage;
	private int pageSize;
	private int totalPages;
	private Long totalItems;
	private Object results;
}
