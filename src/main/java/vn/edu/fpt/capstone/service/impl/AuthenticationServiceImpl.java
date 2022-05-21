package vn.edu.fpt.capstone.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.constant.Constant;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.SignInDto;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.model.UserPrincipal;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.response.JwtResponse;
import vn.edu.fpt.capstone.security.JwtTokenUtil;
import vn.edu.fpt.capstone.service.AuthenticationService;
import vn.edu.fpt.capstone.validate.Validation;
import vn.edu.fpt.capstone.random.RandomString;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class.getName());

	private String regex_username = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
	private String regex_password = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private Validation validation;

	@Autowired
	private Constant constant;
	
	@Autowired RandomString random;

	@Override
	public ResponseEntity<?> authenticate(SignInDto signInDto) throws AuthenticationException {
		return signInDto.getSignInOption() == constant.Sign_In_Normal ? signInNormal(signInDto)
				: signInByGoogle(signInDto);
	}

	private ResponseEntity<?> signInByGoogle(SignInDto signInDto) {
		if (StringUtils.isEmpty(signInDto.getEmail().trim())) {
			logger.error("Authenticate request: email invalid!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: email invalid!").build());

		}

		UserModel user = userRepository.findByEmail(signInDto.getEmail()).orElse(null);

		return user == null ? createByEmail(signInDto) : signInByEmail(user, signInDto);
	}

	private ResponseEntity<?> signInByEmail(UserModel user, SignInDto signInDto) {
		if (!user.getEmail().equalsIgnoreCase(signInDto.getEmail())) {
			logger.error("Authenticate request: email or ggid not mapping in db!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: email or ggid not mapping in db!").build());
		}

		if (!user.isActive() || user.isDelete()) {
			logger.error("Account locked or deleted!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: account locked or deleted!").build());
		}

		final UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService
				.loadUserByUsername(user.getUsername());

		JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(userPrincipal));

		return ResponseEntity.status(HttpStatus.OK).body(
				ResponseObject.builder().code("200").message("Get token: successfully!").results(jwtResponse).build());
	}

	private ResponseEntity<?> createByEmail(SignInDto signInDto) {
		SignUpDto signUpDto = convertToSignUpDto(signInDto);
		
		// save user in to DB
		UserModel user = userServiceImpl.createUser(signUpDto);

		return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("sign up request: create user successfully!").results(user).build());
	}

	private SignUpDto convertToSignUpDto(SignInDto signInDto) {
		SignUpDto signUpDto = new SignUpDto();
		signUpDto.setEmail(signInDto.getEmail());
		signUpDto.setFirstName(signInDto.getFirstName());
		signUpDto.setLastName(signInDto.getLastName());
		signUpDto.setImageLink(signInDto.getImageLink());
		signUpDto.setRole(signInDto.getRole());
		signUpDto.setUsername("hola"+ random.generateUsername(4));
		String password = random.generatePassword(8);
		signUpDto.setPassword(password);
		return signUpDto;
	}

	private ResponseEntity<?> signInNormal(SignInDto signInDto) {
		if (StringUtils.isEmpty(signInDto.getUsername().trim())
				|| StringUtils.isEmpty(signInDto.getPassword().trim())) {
			logger.error("Parameter invalid!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: username or password invalid!!").build());

		}

		UserModel user = userRepository.findByUsername(signInDto.getUsername()).get();

		if (!user.isActive() || user.isDelete()) {
			logger.error("Account locked or deleted!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
					.message("Authenticate request: account locked or deleted!").build());
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService
				.loadUserByUsername(signInDto.getUsername());

		JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(userPrincipal));

		return ResponseEntity.status(HttpStatus.OK).body(
				ResponseObject.builder().code("200").message("Get token: successfully!").results(jwtResponse).build());
	}

	@Override
	public ResponseEntity<?> signUpVerify(SignUpDto signUpDto) {
		// Validate user name
		// user name is 8-20 characters long
		// no _ or . at the beginning
		// no __ or _. or ._ or .. inside
		// allowed characters
		// no _ or . at the end
		if (!validation.checkRegex(regex_username, signUpDto.getUsername())) {
			logger.error("Username invalid!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("sign up request: username invalid!").build());
		}

		// Validate password
		// Minimum eight characters, at least one letter and one number
		if (!validation.checkRegex(regex_password, signUpDto.getPassword())) {
			logger.error("P invalid!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseObject.builder().code("400").message("sign up request: password invalid!").build());
		}

		// Check for user name exists in a DB
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			logger.error("User name has exits!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					ResponseObject.builder().code("400").message("sign up request: user name has exits!").build());
		}

		// save user in to DB
		UserModel user = userServiceImpl.createUser(signUpDto);

		return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
				.message("sign up request: create user successfully!").results(user).build());

	}

}
