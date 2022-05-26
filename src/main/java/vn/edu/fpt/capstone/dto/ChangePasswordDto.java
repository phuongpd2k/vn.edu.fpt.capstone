package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChangePasswordDto {
	@JsonProperty("oldPassword")
	private String oldPassword;
	
	@JsonProperty("newPassword")
	private String newPassword;
}
