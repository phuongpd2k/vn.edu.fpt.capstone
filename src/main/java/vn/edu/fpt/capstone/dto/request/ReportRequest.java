package vn.edu.fpt.capstone.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReportRequest {
	private Long postId;
	private String content;
}
