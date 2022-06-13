package vn.edu.fpt.capstone.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import vn.edu.fpt.capstone.dto.ImageDto;

@Data
public class PostResponse {
	private Long id;
	private Long postTypeId;
	private String postType;
	private String roomName;
	private String houseName;
	private Date startDate;
	private Date endDate;
	private int cost;
	private int numberOfDays;
	private String status;
	
	private String roomType;
	private String roomCategory;
	private double area;
	private int rentalPrice;
	
	private String street;
	private String phuongXa;
	private String quanHuyen;
	private String thanhPho;
	
	private String hostName;
	private String hostPhone;
	
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate","id"})
	private List<ImageDto> images;
}
