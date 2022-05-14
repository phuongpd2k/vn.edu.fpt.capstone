package vn.edu.fpt.capstone.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.response.UserResponse;
import vn.edu.fpt.capstone.controller.AuthenticationController;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.SignInDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.model.UserPrincipal;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.response.JwtResponse;
import vn.edu.fpt.capstone.security.JwtTokenUtil;
import vn.edu.fpt.capstone.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class.getName());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

	@Override
	public ResponseEntity<?> authenticate(SignInDto signInDto) throws AuthenticationException {
		ResponseObject response = new ResponseObject();
		
		if (StringUtils.isEmpty(signInDto.getUsername().trim())
                || StringUtils.isEmpty(signInDto.getPassword().trim())) {
            logger.error("Parameter invalid!");
            
            response.setCode("401");
            response.setMessage("authenticate request: username or password invalid!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
		
        UserModel user = userRepository.findByUsername(signInDto.getUsername()).get();

        if (!user.isActive() || user.isDelete()) {
            logger.error("Account locked!");
            
            response.setCode("401");
            response.setMessage("authenticate request: account locked!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getUsername(), signInDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        final UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService
                .loadUserByUsername(signInDto.getUsername());
        
        JwtResponse jwtResponse = generateJwtResponse(userPrincipal);
        
        return ResponseEntity.ok(jwtResponse);
	}

	private JwtResponse generateJwtResponse(UserPrincipal userPrincipal) {
		final String token = jwtTokenUtil.generateToken(userPrincipal);
		
		Set<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
		
		return new JwtResponse(token, new UserResponse(userPrincipal.getUsername(), roles));
	}

}
