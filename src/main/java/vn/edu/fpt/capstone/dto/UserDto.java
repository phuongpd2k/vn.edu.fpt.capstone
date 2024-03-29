package vn.edu.fpt.capstone.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties({"password"})
public class UserDto {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	@JsonProperty("imageLink")
	private String imageLink;
	@JsonProperty("gender")
	private boolean gender;
	@JsonProperty("dob")
	private String dob;
}
