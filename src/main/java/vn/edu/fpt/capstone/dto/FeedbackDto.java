package vn.edu.fpt.capstone.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FeedbackDto extends Auditable<String> {
	private Long id;
	private Long userId;
	private String content;
	private float rating;
	private Long postId;

	public FeedbackDto() {

	}

	public FeedbackDto(Long userId, String content, float rating, Long postId) {
		super();
		this.userId = userId;
		this.content = content;
		this.rating = rating;
		this.postId = postId;
	}

	public FeedbackDto(Long id, Long userId, String content, float rating, Long postId) {
		super();
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.rating = rating;
		this.postId = postId;
	}

}
