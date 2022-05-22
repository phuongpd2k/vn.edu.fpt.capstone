package vn.edu.fpt.capstone.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomDto extends Auditable<String> {
	private Long id;
	private Long houseId;
	private Long roomCategoryId;
	private Long roomTypeId;
	private String name;
	private String area;
	private int maximumNumberOfPeople;
	private String rentalPrice;
	private boolean deposit;
	private String status;
	private boolean enable;
	private String electricityPriceByNumber;
	private String waterPricePerMonth;
	private String description;
	private String introImageUrl;
	private Long roomImageId;
}
