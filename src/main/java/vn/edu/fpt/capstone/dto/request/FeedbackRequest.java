package vn.edu.fpt.capstone.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FeedbackRequest {
	private String content;
	private float rating;
	private Long postId;
}
