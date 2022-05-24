package vn.edu.fpt.capstone.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.service.MailService;

@Service
public class MailServiceImpl implements MailService{
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class.getName());
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendMailVerifyCode(String email, String username, String code) throws MessagingException, UnsupportedEncodingException {
		String subject = "Please verify your registration";
		String senderName = "Hola Boarding House";
		String verifyURL = "localhost:8080/api/v1/verify?code=" + code;
		
//		String mailContent = "<p>Dear " + username + ",</p>";
//		mailContent += "<p>Please click the link below to verify to your registration:</p>";
//		mailContent += "<h3><a href=\"" + verifyURL +"\">VERIFY</a></h3>";
//		mailContent += "<p>Thank you<br>The Hola Team!</p>";
		
		String mailContent = "Dear [[name]],<br><br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "The Hola Team!";
		
		mailContent = mailContent.replace("[[name]]", username);
		mailContent = mailContent.replace("[[URL]]", verifyURL);
		
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("truongdung07062000@gmail.com", senderName);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            javaMailSender.send(message);
            
            logger.info("email sent to email: " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}

}
