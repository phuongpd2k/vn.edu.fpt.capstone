package vn.edu.fpt.capstone.response;

import java.io.Serializable;

/*
This is class is required for creating a response containing the JWT to be returned to the user.
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private UserResponse user;

    public JwtResponse(String jwttoken, UserResponse user) {
		this.jwttoken = jwttoken;
		this.user = user;
	}

    public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}