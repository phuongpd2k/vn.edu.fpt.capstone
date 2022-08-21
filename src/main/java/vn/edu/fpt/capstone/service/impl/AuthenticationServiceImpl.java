package vn.edu.fpt.capstone.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.ChangePasswordDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.RoleDto;
import vn.edu.fpt.capstone.dto.SignInDto;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.model.UserPrincipal;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.response.JwtResponse;
import vn.edu.fpt.capstone.security.JwtTokenUtil;
import vn.edu.fpt.capstone.service.AuthenticationService;
import vn.edu.fpt.capstone.service.MailService;
import vn.edu.fpt.capstone.service.RoleService;
import vn.edu.fpt.capstone.service.UserService;
import vn.edu.fpt.capstone.validate.Validation;
import vn.edu.fpt.capstone.random.RandomString;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class.getName());

	private String regex_username = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
	private String regex_password = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
	private String regex_email = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private Validation validation;

	@Autowired
	private RandomString random;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<?> authenticate(SignInDto signInDto) throws AuthenticationException {
		if (signInDto.getUsername()==null || signInDto.getPassword()==null) {
			logger.error("Parameter invalid!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ResponseObject.builder().code("401")
							.message("Authenticate request: username or password invalid!!")
							.messageCode("USERNAME_PASSWORD_INVALID").build());

		}
		if (StringUtils.isEmpty(signInDto.getUsername().trim())
				|| StringUtils.isEmpty(signInDto.getPassword().trim())) {
			logger.error("Parameter invalid!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ResponseObject.builder().code("401")
							.message("Authenticate request: username or password invalid!!")
							.messageCode("USERNAME_PASSWORD_INVALID").build());

		}
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));

		Optional<UserModel> userOption = userRepository.findByUsername(signInDto.getUsername());
		UserModel user = null;
		if(userOption.isPresent()) {
			user=userOption.get();
		}
		if (user == null || !passwordEncoder.matches(signInDto.getPassword(), user.getPassword()) 
				|| !user.getUsername().equals(signInDto.getUsername())) {
			logger.error("username or pasword wrong!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ResponseObject.builder().code("401").message("sign in request: username or pasword wrong!")
							.messageCode("USERNAME_PASSWORD_WRONG").build());
		}
		

		if (!user.isActive()) {
			logger.error("Account inactive!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: account lock!").messageCode("INACTIVE_ACCOUNT").build());
		}

		if (!user.isVerify()) {
			logger.error("unverified account!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: unverified account!").messageCode("UNVERIFIED_ACCOUNT").build());
		}
		
		if (!user.isEnable()) {
			logger.error("Account deleted!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: account deleted!").messageCode("DELETED_ACCOUNT").build());
		}

		
		//SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService
				.loadUserByUsername(user.getEmail());

		JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(userPrincipal));

		return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("Get token signin: successfully!").results(jwtResponse).build());
	}

	@Override
	public ResponseEntity<?> verify(String code) {
		UserModel userModel = userService.findByVerificationCode(code);

		if (userModel.getVerificationCode().equalsIgnoreCase(code)) {
			userModel.setActive(true);
			userModel.setVerify(true);
			userRepository.save(userModel);
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Verify code: verify code successfully!").messageCode("VERIFY_CODE_SUCCESSFULLY").build());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("401")
				.message("Verify code: verify code fail!").messageCode("VERIFY_CODE_FAIL").build());
	}

	@Override
	public ResponseEntity<?> changePassword(ChangePasswordDto changePasswordDto, UserModel user) {
		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("401")
					.message("Change password: fail!").messageCode("CHANGE_PASSWORD_FAIL").build());
		}

		if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
			logger.error("Old password is incorrect!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("401").message("Change password: Old password is incorrect!")
							.messageCode("OLD_PASWORD_INCORRECT").build());
		}

		// Validate password
		// Minimum eight characters, at least one letter and one number
		if (!validation.checkRegex(regex_password, changePasswordDto.getNewPassword())
				|| changePasswordDto.getNewPassword().isEmpty()) {
			logger.error("New password invalid!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Change password: new password invalid!").messageCode("NEW_PASSWORD_INVALID").build());
		}

		String newPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
		user.setPassword(newPassword);

		userRepository.save(user);

		return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("Change password: successfully!").messageCode("CHANGE_PASSWORD_SUCCESSFULLY").build());
	}

	@Override
	public ResponseEntity<?> signUpNormal(SignUpDto signUpDto) {
		// Validate email
		if (!validation.checkRegex(regex_email, signUpDto.getEmail()) || signUpDto.getEmail().isEmpty()) {
			logger.error("Email invalid!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("sign up request: email invalid!").messageCode("EMAIL_INVALID").build());
		}

		// Validate user name
		// user name is 8-20 characters long
		// no _ or . at the beginning
		// no __ or _. or ._ or .. inside
		// allowed characters
		// no _ or . at the end
		if (!validation.checkRegex(regex_username, signUpDto.getUsername()) || signUpDto.getUsername().isEmpty()) {
			logger.error("Username invalid!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("sign up [user name]: username invalid!").messageCode("USERNAME_INVALID").build());
		}

		// Validate password
		// Minimum eight characters, at least one letter and one number
		if (!validation.checkRegex(regex_password, signUpDto.getPassword()) || signUpDto.getPassword().isEmpty()) {
			logger.error("Email invalid!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("sign up [password]: password invalid!").messageCode("PASSWORD_INVALID").build());
		}

		// Check for User name exists in a DB
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			logger.error("User name has exits!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("sign up request: user name has exist!").messageCode("USERNAME_HAS_EXISTED").build());
		}

		// Check for email exists in a DB
		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			logger.error("Email has exits!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("sign up request: Email has exits!").messageCode("EMAIL_HAS_EXISTED").build());
		}

		// Verify
		String verifyCode = signUpDto.getUsername() + random.generateCode(20);
		signUpDto.setVerificationCode(verifyCode);
		signUpDto.setActive(true);
		signUpDto.setVerify(false);

		// Send mail verify
		try {
			mailService.sendMailVerifyCode(signUpDto.getEmail(), signUpDto.getUsername(), verifyCode);
		} catch (UnsupportedEncodingException e) {
			logger.error("Send mail: " + e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Send email: " + e.getMessage()).messageCode("ERROR_SEND_EMAIL").build());
		} catch (MessagingException e) {
			logger.error("Send mail: " + e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("Send email: " + e.getMessage()).messageCode("ERROR_SEND_EMAIL").build());
		}

		// save user in to DB
		userService.createUser(signUpDto);

		return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("sign up request: create user successfully!").messageCode("SIGN_UP_SUCCESSFULLY").build());
	}

	@Override
	public ResponseEntity<?> signUpByEmail(SignUpDto signUpDto) {
		// Check for email exists in a DB
		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			logger.error("Email has exits!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
					.message("sign up request: Email has exits!").messageCode("EMAIL_HAS_EXISTED").build());
		}

		signUpDto.setUsername("hola" + random.generateUsername(4));
		signUpDto.setPassword(random.generatePassword(8));
		signUpDto.setActive(true);
		signUpDto.setVerify(true);

		// save user in to DB
		UserModel user = userService.createUser(signUpDto);

		final UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService
				.loadUserByUsername(user.getEmail());

		JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(userPrincipal));

		return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("Get token sign in by email: successfully!").results(jwtResponse).build());
	}

	private SignUpDto convertToSignUpDto(SignInDto signInDto) {
		SignUpDto signUpDto = new SignUpDto();
		signUpDto.setEmail(signInDto.getEmail());
		signUpDto.setFullName(signInDto.getFullName());
		signUpDto.setImageLink(signInDto.getImageLink());
		signUpDto.setRole(modelMapper.map(roleService.getRoleByCode("ROLE_USER"), RoleDto.class));
		signUpDto.setUsername("hola" + random.generateUsername(4));
		signUpDto.setPassword(random.generatePassword(8));
		return signUpDto;
	}

	@Override
	public ResponseEntity<?> authenticateByEmail(SignInDto signInDto) {
		if (StringUtils.isEmpty(signInDto.getEmail().trim())) {
			logger.error("Authenticate request: email invalid!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ResponseObject.builder().code("401").message("Authenticate request: email invalid!").build());

		}

		UserModel user = userRepository.findByEmail(signInDto.getEmail()).orElse(null);

		if (user == null) {
			SignUpDto signUpDto = convertToSignUpDto(signInDto);
			signUpDto.setVerify(true);
			signUpDto.setActive(true);

			// save user in to DB
			UserModel userNew = userService.createUser(signUpDto);

			final UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService
					.loadUserByUsername(userNew.getEmail());

			JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(userPrincipal));

			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Get token sign in by email: successfully!").results(jwtResponse).build());

		}

		if (!user.isActive()) {
			logger.error("Account inactive!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: account inactive!").messageCode("ACCOUNT_INACTIVE").build());
		}

		if (!user.isVerify()) {
			user.setVerify(true);
			user = userRepository.save(user);
		}
		if (!user.isEnable()) {
			logger.error("Account deleted!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: account deleted!").messageCode("DELETED_ACCOUNT").build());
		}

		final UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService
				.loadUserByUsername(user.getEmail());

		JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(userPrincipal));

		return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("Get token sign in by email: successfully!").results(jwtResponse).build());
	}

	@Override
	public ResponseEntity<?> resetPassword(UserModel user) {
		String newPassword = passwordEncoder.encode(random.generatePassword(8));
		user.setPassword(newPassword);
		user.setResetCode(random.generateCode(6));
		userService.updateUser(modelMapper.map(user, UserDto.class));
		return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("Reset password: successfully!").messageCode("RESET_PASSWORD_SUCCESSFULLY").build());
	}

	@Override
	public ResponseEntity<?> verifyByUserId(Long id) {
		UserModel userModel = userService.getUserInformationById(id);

		if (userModel != null) {
			userModel.setActive(true);
			userModel.setVerify(true);
			userRepository.save(userModel);
			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Verify code: verify code successfully!").messageCode("VERIFY_SUCCESSFULLY").build());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("401")
				.message("Verify code: verify code fail!").messageCode("VERIFY_FAIL").build());
	}

}
