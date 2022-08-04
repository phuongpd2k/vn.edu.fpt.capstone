package vn.edu.fpt.capstone.dto;

import java.util.List;

import lombok.Data;

@Data
public class FilterRoomDto extends SearchDto{
	private String verify;
	private Double minArea;
	private Double maxArea;
	private List<Long> typeOfRentalIds;
	private List<Long> roomCategoryIds;
	private Integer minPrice;
	private Integer maxPrice;
	private Integer maximumNumberOfPeople;
	private List<Long> amenityHouseIds;
	private List<Long> amenityRoomIds;
	private String roomMate;
}
