package vn.edu.fpt.capstone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FavoriteRequest {
	@JsonProperty(index = 1)
	private Long postId;
}
