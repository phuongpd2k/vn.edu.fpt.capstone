package vn.edu.fpt.capstone.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.RoomDto;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PostingRoomResponse {
	@JsonIgnoreProperties({"postType","createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", 
		"startDate", "endDate", "numberOfDays", "cost", "status", "note", "verify", "post_type", "postCost"})
	private PostDto post;
	
	@JsonIgnoreProperties({"house"})
	private List<RoomDto> rooms;
	
	private int minPrice;
	private int maxPrice;
	private float minArea;
	private float maxArea;
	
	private String street;
	private String phuongXa;
	private String quanHuyen;
	private String thanhPho;
}
