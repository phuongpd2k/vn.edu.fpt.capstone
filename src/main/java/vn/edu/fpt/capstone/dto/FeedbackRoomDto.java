package vn.edu.fpt.capstone.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;
@Data
@EqualsAndHashCode(callSuper = false)
public class FeedbackRoomDto extends Auditable<String>{
	@JsonProperty(index = 0)
    private Long id;
	@JsonProperty(index = 1)
    private Long userId;
	@JsonProperty(index = 2)
    private String content;
	@JsonProperty(index = 3)
    private int numberOfStar;
	@JsonProperty(index = 4)
    private Long roomId;
}
