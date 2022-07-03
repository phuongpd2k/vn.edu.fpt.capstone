package vn.edu.fpt.capstone.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.CategoryTypeDto;
import vn.edu.fpt.capstone.dto.ImageDto;

@Data
public class RoomPostingResponse{
	private Long id;
	
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate","id"})
	private List<ImageDto> images;
	
	private String roomName;
	private String roomType;
	private double area;	
	private int maximumNumberOfPeople;
	private int rentalPrice;		
	private boolean statusRental;	
	private int electricityPriceByNumber;	
	private int waterPricePerMonth;	
	private String roomDescription;
	private int floor;
	private String timeType;
	private String linkFb;
	
	private String street;
	private String phuongXa;
	private String quanHuyen;
	private String thanhPho;
	
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate","id", "type"})
	private List<AmenityDto> amenitiesRoom;
	
	private String roomCategoryName;
	private String roomCategoryDescription;
	
	private String houseName;
	private String houseDescription;
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate","id", "type"})
	private List<AmenityDto> amenitiesHouse;
	
	private String hostName;
	private String hostPhone;
	private String imageLinkHost;
	
	private List<CategoryTypeDto> categoryTypes;
}
