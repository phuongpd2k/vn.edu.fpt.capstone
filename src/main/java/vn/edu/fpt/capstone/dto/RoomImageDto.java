package vn.edu.fpt.capstone.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomImageDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
    private String name;
	@JsonProperty(index = 2)
    private Long roomId;
	@JsonProperty(index = 3)
    private Long imageId;
}
