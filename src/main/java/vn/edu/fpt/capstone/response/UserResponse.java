package vn.edu.fpt.capstone.response;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

public class UserResponse {
	private long id;

    private String username;
    
    private String role;
    
    public UserResponse() {
	}
    
    public UserResponse(String username, String role) {
    	this.username = username;
    	this.role = role;
	}

    
//    public UserResponse userResponse(UserDetails userDetails, Set<String> roles) {
//        UserResponse u = new UserResponse();
//        u.setUsername(userDetails.getUsername());
//        u.setRoles(roles);
//        return u;
//    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


}
