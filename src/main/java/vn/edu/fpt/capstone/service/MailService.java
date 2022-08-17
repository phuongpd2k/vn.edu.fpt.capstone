package vn.edu.fpt.capstone.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
	void sendMailVerifyCode(String email, String username, String code) throws MessagingException, UnsupportedEncodingException;
	
	void sendMailResetPassword(String email) throws MessagingException, UnsupportedEncodingException;

	void sendMailLockUser(String email, String username, String note) throws MessagingException, UnsupportedEncodingException;

	void sendMailVerifyFail(String email, String username, String verifyNote) throws MessagingException, UnsupportedEncodingException;

}
