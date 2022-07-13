package vn.edu.fpt.capstone.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@JsonIgnoreProperties({ "password" })
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
	private String fullName;

	@JsonProperty(index = 5)
	private String phoneNumber;

	@JsonProperty(index = 6)
	private String imageLink;

	@JsonProperty(index = 7)
	private boolean gender;

	@JsonProperty(index = 8)
	private Date dob;

	@JsonProperty(index = 9)
	private boolean isActive;

	@JsonProperty(index = 10)
	private boolean isVerify;

	@JsonProperty(index = 11)
	private RoleDto role;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String resetCode;

	@JsonProperty(index = 12)
	private float balance;
	
	@JsonProperty(index = 13)
	private String codeTransaction;
	
	@JsonProperty(index = 14)
	private String cccd;
	
	public UserDto(Long id, String username, String email, String password, String fullName, String phoneNumber,
			String imageLink, boolean gender, Date dob, boolean isActive, RoleDto role, String resetCode, float balance,
			String codeTransaction, String cccd) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.imageLink = imageLink;
		this.gender = gender;
		this.dob = dob;
		this.isActive = isActive;
		this.role = role;
		this.resetCode = resetCode;
		this.balance = balance;
		this.codeTransaction = codeTransaction;
		this.cccd = cccd;
	}
	public UserDto(Long id) {
		super();
		this.id = id;
	}
	public UserDto() {
	}
}
