package vn.edu.fpt.capstone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.ChangePasswordDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.SignInDto;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.service.AuthenticationService;
import vn.edu.fpt.capstone.service.MailService;
import vn.edu.fpt.capstone.service.UserService;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class.getName());
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@PostMapping(value = "/signup")
	public ResponseEntity<?> signUpNormal(@RequestBody SignUpDto signUpDto) {
		try {
			return authenticationService.signUpNormal(signUpDto);
		} catch (Exception e) {
			logger.error(e.toString());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Sign up failed: " + e.getMessage()).messageCode("SIGN_UP_FAILED").build());
		}
	}

	@PostMapping(value = "/signup-by-email")
	public ResponseEntity<?> signUpByEmail(@RequestBody SignUpDto signUpDto) {
		try {
			return authenticationService.signUpByEmail(signUpDto);
		} catch (Exception e) {
			logger.error(e.toString());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Sign up failed: " + e.getMessage()).messageCode("SIGN_UP_FAILED").build());
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody SignInDto signInDto) {
		try {
			return authenticationService.authenticate(signInDto);
		} catch (AuthenticationException e) {
			logger.error("Authenticate failed for username: " + signInDto.getUsername());
			logger.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ResponseObject.builder().code("401").message("sign in request: username or pasword wrong!")
							.messageCode("USERNAME_PASSWORD_WRONG").build());
		} catch (Exception e) {
			logger.error("Undefined error");
			logger.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("sign in undefined error: " + e.getMessage()).messageCode("SIGN_IN_FAIL").build());

		}
	}

	@PostMapping("/signin-by-email")
	public ResponseEntity<?> authenticateByEmail(@RequestBody SignInDto signInDto) {
		try {
			return authenticationService.authenticateByEmail(signInDto);
		} catch (AuthenticationException e) {
			logger.error("Authenticate failed for username: " + signInDto.getUsername());
			logger.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ResponseObject.builder().code("401").message("sign in request: username or pasword wrong!")
							.messageCode("USERNAME_PASSWORD_WRONG").build());
		} catch (Exception e) {
			logger.error("Undefined error");
			logger.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("sign in undefined error: " + e.getMessage()).messageCode("SIGN_IN_FAIL").build());

		}
	}

	@PutMapping("/verify")
	public ResponseEntity<?> verify(@Param("code") String code) {
		try {
			return authenticationService.verify(code);
		} catch (Exception e) {
			logger.error("Verify code new account!");
			logger.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("401")
					.message("Verify code: verify code fail!").messageCode("VERIFY_CODE_FAIL").build());
		}
	}

	// @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestHeader(value = "Authorization") String jwtToken,
			@RequestBody ChangePasswordDto changePasswordDto) {
		try {
			UserModel user = userService.getUserInformationByToken(jwtToken);
			return authenticationService.changePassword(changePasswordDto, user);
		} catch (Exception e) {
			logger.error("Undefined error change password");
			logger.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400")
							.message("Change password undefined error: " + e.getMessage())
							.messageCode("CHANGE_PASSWORD_FAIL").build());

		}
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestParam(value = "email", required = false) String email) {
		try {
			if (!(email == null || email.trim().isEmpty())) {
				UserModel userModel = userService.findByEmail(email);
				if (userModel != null) {
					mailService.sendMailResetPassword(email);
				}
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Successful").messageCode(Message.OK).build());
		} catch (Exception e) {
			logger.error("Undefined error change password");
			logger.error("Exception {}", e);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("500")
					.message(e.getMessage()).messageCode(Message.INTERNAL_SERVER_ERROR).build());

		}
	}

//	@PostMapping("/reset")
//	public ResponseEntity<?> resetPassword(@RequestParam(value = "email", required = false) String email,
//			@RequestParam(value = "resetCode", required = false) String code) {
//		try {
//			UserModel userModel = userService.findByEmail(email);
//			logger.info("model: {}", userModel);
//			if (userModel != null) {
//				if (userModel.getResetCode() == code) {
//					return authenticationService.resetPassword(userModel);
//				}
//			}
//			return ResponseEntity.status(HttpStatus.OK)
//					.body(ResponseObject.builder().code("200").message("Successfully").messageCode(Message.OK).build());
//		} catch (Exception e) {
//			logger.error("Undefined error reset password");
//			logger.error("Exception {}", e);
//
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
//					.message(e.getMessage()).messageCode(Message.INTERNAL_SERVER_ERROR).build());
//
//		}
//	}
}
