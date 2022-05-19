package vn.edu.fpt.capstone.response;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserResponse {
	private long id;

    private String username;
    
    private Set<String> roles;
    
    public UserResponse() {
	}
    
    public UserResponse(String username, Set<String> roles) {
    	this.username = username;
    	this.roles = roles;
	}

    
    public UserResponse userResponse(UserDetails userDetails, Set<String> roles) {
        UserResponse u = new UserResponse();
        u.setUsername(userDetails.getUsername());
        u.setRoles(roles);
        return u;
    }

    

}
