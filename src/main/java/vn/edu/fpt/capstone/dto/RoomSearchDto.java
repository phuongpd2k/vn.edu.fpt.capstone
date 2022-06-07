package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RoomSearchDto extends SearchDto{
	@JsonProperty("isActive")
	private boolean isActive;
}
