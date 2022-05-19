package vn.edu.fpt.capstone.dto;

import java.util.Date;

import lombok.Data;
import vn.edu.fpt.capstone.model.RoleModel;

@Data
public class SignUpDto {
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String imageLink;
	private boolean gender;
	private Date dob;
    private RoleModel role;
}
