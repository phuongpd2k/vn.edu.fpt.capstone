package vn.edu.fpt.capstone.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
public class RoomDto extends Auditable<String> {
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("roomType")
	private RoomTypeDto roomType;
	
	@JsonProperty("roomCategory")
	private RoomCategoryDto roomCategory;
	
	@JsonProperty("house")
	private HouseDto house;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("area")
	private double area;
	
	@JsonProperty("maximumNumberOfPeople")
	private int maximumNumberOfPeople;
	
	@JsonProperty("rentalPrice")
	private int rentalPrice;
	
	@JsonProperty("deposit")
	private int deposit;
	
	@JsonProperty("status")
	private boolean status_rental;
	
	@JsonProperty("enable")
	private boolean enable = true;
	
	@JsonProperty("electricityPriceByNumber")
	private int electricityPriceByNumber;
	
	@JsonProperty("waterPricePerMonth")
	private int waterPricePerMonth;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("introImageUrl")
	private String introImageUrl;
	
	@JsonProperty("amenities")
	private Collection<AmenityDto> amenities;
	
	@JsonProperty("images")
	private Collection<ImageDto> images;
	
	@JsonProperty("floor")
	private int floor;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("roomMate")
	private String roomMate;
	
	private boolean check = false;
}
