package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FeedbackDto extends Auditable<String> {
	private Long id;
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate","id","phoneNumber","gender","dob","active","verify","role","balance","codeTransaction","cccd"})
	private UserDto user;
	private String content;
	private float rating;
	private Long postId;

	public FeedbackDto() {

	}

	public FeedbackDto(UserDto user, String content, float rating, Long postId) {
		super();
		this.user = user;
		this.content = content;
		this.rating = rating;
		this.postId = postId;
	}

	public FeedbackDto(Long id, UserDto user, String content, float rating, Long postId) {
		super();
		this.id = id;
		this.user = user;
		this.content = content;
		this.rating = rating;
		this.postId = postId;
	}

}
