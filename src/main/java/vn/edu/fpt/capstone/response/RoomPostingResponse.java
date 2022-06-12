package vn.edu.fpt.capstone.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import vn.edu.fpt.capstone.dto.AmenityDto;
import vn.edu.fpt.capstone.dto.ImageDto;

@Data
public class RoomPostingResponse{
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
	
	private String street;
	private String phuongXa;
	private String quanHuyen;
	private String thanhPho;
	
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate","id", "type"})
	private List<AmenityDto> amenities;
	
	private String roomCategoryName;
	private String roomCategoryDescription;
	
	private String houseName;
	private String houseDescription;
	
	private String hostName;
	private String hostPhone;
	private String imageLinkHost;
	
	
	//private RoomCategoryDto roomCategory;
	
	//private HouseDto house;

	
	
	
	

	
}
