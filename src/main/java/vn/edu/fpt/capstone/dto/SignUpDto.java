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
	
	@JsonProperty("fullName")
	private String fullName;
	
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
	
    private boolean isVerify;
    
    private boolean isActive;
	
	private String verificationCode;
}
