package vn.edu.fpt.capstone.dto;

import java.util.List;

import lombok.Data;

@Data
public class FilterRoomDto extends SearchDto{
	private List<Long> houseTypeIds;
	private int minPrice;
	private int maxPrice;
	private List<Long> roomCategoryIds;
	private int maximumNumberOfPeople;
	private List<Long> amenityIds;
}
