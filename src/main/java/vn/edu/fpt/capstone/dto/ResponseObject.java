package vn.edu.fpt.capstone.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseObject {
	private String code;
	private String message;
	private Object results;
}
