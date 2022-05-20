package vn.edu.fpt.capstone.validate;

import org.springframework.stereotype.Component;

@Component
public class Validation {
	public boolean checkRegex(String regex, String content) {
		return content.matches(regex);
	}
}
