package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import vn.edu.fpt.capstone.model.RoleModel;

@Data
public class SignInDto {
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("fullName")
	private String fullName;
	
	@JsonProperty("imageLink")
	private String imageLink;
}
