package vn.edu.fpt.capstone.dto;

import java.util.List;

import lombok.Data;

@Data
public class FilterRoomDto extends SearchDto{
	private String verify;
	private double minArea;
	private double maxArea;
	private List<Long> typeOfRentalIds;
	private List<Long> roomCategoryIds;
	private int minPrice;
	private int maxPrice;
	private int maximumNumberOfPeople;
	private List<Long> amenityIds;
	private String roomMate;
}
