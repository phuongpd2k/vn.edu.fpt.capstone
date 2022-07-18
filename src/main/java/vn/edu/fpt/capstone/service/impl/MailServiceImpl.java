package vn.edu.fpt.capstone.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.random.RandomString;
import vn.edu.fpt.capstone.service.MailService;
import vn.edu.fpt.capstone.service.UserService;

@Service
public class MailServiceImpl implements MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class.getName());

	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${host.domain}")
	private String domain;
	@Value("${spring.mail.username}")
    private String sendFrom;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	UserService userService;
	@Autowired
	private RandomString random;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void sendMailVerifyCode(String email, String username, String code)
			throws MessagingException, UnsupportedEncodingException {
		String subject = "Xác minh tài khoản";
		String senderName = "Hola Houses";
		String verifyURL = "https://holahouses.netlify.app/verify-account?code=" + code;

		String mailContent = "Dear [[name]],<br><br>" + "Click vào đường link bên dưới để xác minh tài khoản của bạn:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">XÁC NHẬN</a></h3>" + "Cảm ơn,<br>" + "The Hola Team!";

		mailContent = mailContent.replace("[[name]]", username);
		mailContent = mailContent.replace("[[URL]]", verifyURL);

		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(sendFrom, senderName);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(mailContent, true);
			javaMailSender.send(message);

			logger.info("email sent to email: " + email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendMailResetPassword(String email) throws UnsupportedEncodingException {
		String subject = "Đặt lại mật khẩu";
		String senderName = "Hola Houses";
		String newPass = random.generatePassword(8);
		String newPassword = passwordEncoder.encode(newPass);
		UserModel userModel = userService.findByEmail(email);
		userModel.setPassword(newPassword);
		userModel.setResetCode(random.generateCode(6));

		String userName = userModel.getUsername();
		MimeMessage message = javaMailSender.createMimeMessage();
		boolean multipart = true;

		String mailContent = "Dear [[name]],<br><br>" + "Mật khẩu mới của bạn là:<br>" + "<h3>[NEW_PASS]</h3>"
				+ "Cảm ơn,<br>" + "The Hola Team!";

		mailContent = mailContent.replace("[[name]]", userName);
		mailContent = mailContent.replace("[NEW_PASS]", newPass);
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
			message.setContent(mailContent, "text/html");
			helper.setFrom(sendFrom, senderName);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(mailContent, true);
			javaMailSender.send(message);
			userService.updateUser(modelMapper.map(userModel, UserDto.class));
			logger.info("email sent to email: " + email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
