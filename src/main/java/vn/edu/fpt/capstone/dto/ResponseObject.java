package vn.edu.fpt.capstone.dto;

import lombok.Data;

@Data
public class ResponseObject {
	private String code;
	private String message;
	private Object results;
	
	public ResponseObject() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResults() {
		return results;
	}

	public void setResults(Object results) {
		this.results = results;
	}
	
	
}
