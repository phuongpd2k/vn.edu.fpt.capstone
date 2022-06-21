package vn.edu.fpt.capstone.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.fpt.capstone.dto.UserDto;

@Data
@AllArgsConstructor
public class TransactionResponse {
	private String fullname;
//	@JsonIgnoreProperties({ "email", "imageLink", "role", "delete", "active", "dob", "gender",
//		"phoneNumber", "lastName", "firstName", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate",
//		"verify" })
//private UserDto user;
	private float amount;
	private float actualAmount;
	private String code;
	private Date date;
	private String status;
	
}
