package vn.edu.fpt.capstone.dto;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseDto {
	private Date createdAt;
	private String createdBy;
	private Date modifiedAt;
	private String modifiedBy;
}
