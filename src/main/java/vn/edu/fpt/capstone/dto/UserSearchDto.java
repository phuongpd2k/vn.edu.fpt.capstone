package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserSearchDto extends SearchDto{
	//0: inactive, 1: active, 2: all
	@JsonProperty("isActive")
	private int isActive;
	@JsonProperty("fullname")
	private String fullname;
	@JsonProperty("username")
    private String username;
}
