package vn.edu.fpt.capstone.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@JsonIgnoreProperties({ "password" })
@EqualsAndHashCode(callSuper = false)
public class UserDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;

	@JsonProperty(index = 1)
	private String username;

	@JsonProperty(index = 2)
	private String email;

	@JsonProperty(index = 3)
	private String password;

	@JsonProperty(index = 4)
	private String firstName;

	@JsonProperty(index = 5)
	private String lastName;

	@JsonProperty(index = 6)
	private String phoneNumber;

	@JsonProperty(index = 7)
	private String imageLink;

	@JsonProperty(index = 8)
	private boolean gender;

	@JsonProperty(index = 9)
	private Date dob;

	@JsonProperty(index = 10)
	private boolean isActive;

	@JsonProperty(index = 11)
	private boolean isDelete;

	@JsonProperty(index = 12)
	private RoleDto role;
}
