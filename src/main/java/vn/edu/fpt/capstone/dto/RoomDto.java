package vn.edu.fpt.capstone.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.AmenityModel;
import vn.edu.fpt.capstone.model.Auditable;
import vn.edu.fpt.capstone.model.HouseModel;
import vn.edu.fpt.capstone.model.ImageModel;
import vn.edu.fpt.capstone.model.RoomCategoryModel;
import vn.edu.fpt.capstone.model.RoomTypeModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomDto extends Auditable<String> {
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("roomTypeId")
	private Long roomTypeId;
	
	@JsonProperty("roomCategoryId")
	private Long roomCategoryId;
	
	@JsonProperty("houseId")
	private Long houseId;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("area")
	private String area;
	
	@JsonProperty("maximumNumberOfPeople")
	private int maximumNumberOfPeople;
	
	@JsonProperty("rentalPrice")
	private String rentalPrice;
	
	@JsonProperty("deposit")
	private boolean deposit;
	
	@JsonProperty("status")
	private Integer status;
	
	@JsonProperty("enable")
	private boolean enable;
	
	@JsonProperty("electricityPriceByNumber")
	private String electricityPriceByNumber;
	
	@JsonProperty("waterPricePerMonth")
	private String waterPricePerMonth;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("introImageUrl")
	private String introImageUrl;
	
	@JsonProperty("amenitiesId")
	private Set<Long> amenitiesI;
	
	@JsonProperty("imagesId")
	private Set<Long> imagesID;
	
//	private Long roomImageId;
//	private Long roomTypeId;
//	private Long houseId;
//	private Long roomCategoryId;
}
