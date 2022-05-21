package vn.edu.fpt.capstone.random;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomString {

	public String generatePassword(int length) {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		Random random = new Random();
		String password = "";

		password += lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password += capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password += specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		password += numbers.charAt(random.nextInt(numbers.length()));

		for (int i = 4; i < length; i++) {
			password += combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		return password;
	}
	
	public String generateUsername(int length) {
		String lowerCaseLettersU = "abcdefghijklmnopqrstuvwxyz";
		String numbersU = "1234567890";
		String combinedCharsU = lowerCaseLettersU + numbersU;
		Random rd = new Random();
		String username = "";

		username += lowerCaseLettersU.charAt(rd.nextInt(lowerCaseLettersU.length()));
		username += numbersU.charAt(rd.nextInt(numbersU.length()));

		for (int i = 2; i < length; i++) {
			username += combinedCharsU.charAt(rd.nextInt(combinedCharsU.length()));
		}
		return username;
	}

}
