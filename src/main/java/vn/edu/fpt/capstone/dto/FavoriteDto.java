package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FavoriteDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
	private Long userId;
	@JsonProperty(index = 2)
	private Long postId;

	public FavoriteDto() {
	}
	public FavoriteDto(Long userId, Long postId) {
		super();
		this.userId = userId;
		this.postId = postId;
	}
	public FavoriteDto(Long id, Long userId, Long postId) {
		super();
		this.id = id;
		this.userId = userId;
		this.postId = postId;
	}
	
}
