package vn.edu.fpt.capstone.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@JsonIgnoreProperties({"password"})
public class UserDto extends Auditable<String>{
	@JsonProperty("id")
	private Long id;
	
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
	
	@JsonProperty("isActive")
	private boolean isActive;
	
	@JsonProperty("isDelete")
	private boolean isDelete;
	
	@JsonProperty("role")
	private RoleDto role;
}
