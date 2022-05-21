package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import vn.edu.fpt.capstone.model.RoleModel;

@Data
public class SignInDto {
	@JsonProperty("signInOption")
	private int signInOption;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("ggid")
	private String ggid;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("imageLink")
	private String imageLink;
	
	@JsonProperty("role")
	private RoleModel role;
}
