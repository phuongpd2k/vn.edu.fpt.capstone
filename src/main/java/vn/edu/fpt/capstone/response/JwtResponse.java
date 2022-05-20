package vn.edu.fpt.capstone.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
This is class is required for creating a response containing the JWT to be returned to the user.
 */
@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
}