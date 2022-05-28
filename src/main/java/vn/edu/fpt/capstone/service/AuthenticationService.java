package vn.edu.fpt.capstone.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ChangePasswordDto;
import vn.edu.fpt.capstone.dto.SignInDto;
import vn.edu.fpt.capstone.dto.SignUpDto;

@Service
public interface AuthenticationService {
	ResponseEntity<?> authenticate(SignInDto signInDto) throws AuthenticationException;
	
	ResponseEntity<?> verify(String code);
	
	ResponseEntity<?> changePassword(ChangePasswordDto changePasswordDto, String token);

	ResponseEntity<?> signUpNormal(SignUpDto signUpDto);

	ResponseEntity<?> signUpByEmail(SignUpDto signUpDto);

	ResponseEntity<?> authenticateByEmail(SignInDto signInDto);
}
