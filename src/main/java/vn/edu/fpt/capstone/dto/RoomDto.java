package vn.edu.fpt.capstone.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
	private RoomCategoryDto roomCategory;
	@JsonProperty(index = 2)
	private RoomTypeDto roomType;
	@JsonProperty(index = 3)
	private List<RoomImageDto> roomImages = new ArrayList<>();
	@JsonProperty(index = 4)
	private HouseDto house;
	@JsonProperty(index = 5)
	private String name;
	@JsonProperty(index = 6)
	private String area;
	@JsonProperty(index = 7)
	private int maximumNumberOfPeople;
	@JsonProperty(index = 8)
	private String rentalPrice;
	@JsonProperty(index = 9)
	private boolean deposit;
	@JsonProperty(index = 10)
	private String status;
	@JsonProperty(index = 11)
	private boolean enable;
	@JsonProperty(index = 12)
	private String electricityPriceByNumber;
	@JsonProperty(index = 13)
	private String waterPricePerMonth;
	@JsonProperty(index = 14)
	private String description;
	@JsonProperty(index = 15)
	private String introImageUrl;
//	private Long roomImageId;
//	private Long roomTypeId;
//	private Long houseId;
//	private Long roomCategoryId;
}
