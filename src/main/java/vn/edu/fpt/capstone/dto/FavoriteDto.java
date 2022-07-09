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
	private Long roomId;

	public FavoriteDto() {
	}
	public FavoriteDto(Long userId, Long roomId) {
		super();
		this.userId = userId;
		this.roomId = roomId;
	}
	public FavoriteDto(Long id, Long userId, Long roomId) {
		super();
		this.id = id;
		this.userId = userId;
		this.roomId = roomId;
	}
	
}
