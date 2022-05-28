package vn.edu.fpt.capstone.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SignUpDto {
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@JsonProperty("imageLink")
	private String imageLink;
	
	@JsonProperty("gender")
	private boolean gender;
	
	@JsonProperty("dob")
	private Date dob;
	
	@JsonProperty("role")
    private RoleDto role;
	
	@JsonProperty("isActive")
    private boolean isActive;
	
	private String verificationCode;
}
