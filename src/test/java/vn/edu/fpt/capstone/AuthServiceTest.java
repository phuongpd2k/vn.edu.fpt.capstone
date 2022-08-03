//package vn.edu.fpt.capstone;
//
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import vn.edu.fpt.capstone.dto.SignInDto;
//import vn.edu.fpt.capstone.dto.SignUpDto;
//import vn.edu.fpt.capstone.service.AuthenticationService;
//
//
//
//@SpringBootTest
//public class AuthServiceTest {
//	
//		@Autowired 
//		AuthenticationService authenticationService;
//		String email = "15a77d6f67a84359b9ca9e99cba54f2c@gmail.com";
//		String password = "15a77d6f67a84359b9ca9e99cba54f2c@";
//		@DisplayName("signUpByEmail can create new account and return a token for use another services")
//		@Test
//		void whenInputValidInfo_shouldCreateNewAccount() {
//			SignUpDto signUpDto = new SignUpDto();
//			signUpDto.setEmail(email);
//			signUpDto.setPassword(password);
//			authenticationService.signUpByEmail(signUpDto);
//		}
//		
//		@DisplayName("authenticate can return a token for use another services")
//		@Test
//		void whenInputValidAccount_shouldReturnToken() {
//			SignInDto signInDto = new SignInDto();
//			signInDto.setEmail(email);
//			signInDto.setPassword(password);
//			authenticationService.authenticateByEmail(signInDto);
//		}
//}
