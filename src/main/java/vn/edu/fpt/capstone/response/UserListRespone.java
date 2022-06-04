package vn.edu.fpt.capstone.response;

import lombok.Data;

@Data
public class UserListRespone {
	private int totalAccount;
	private int accountInactive;
	private int accountActive;
	private Object data;
	
}
