package vn.edu.fpt.capstone.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.dto.PostDto;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder

public class PostingResponse {
	@JsonIgnoreProperties({"postType","createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", 
		"startDate", "endDate", "numberOfDays", "cost", "status", "note", "verify", "post_type", "postCost"})
	private PostDto post;
	
	private int minPrice;
	private int maxPrice;
	private float minArea;
	private float maxArea;
	
	private String street;
	private String phuongXa;
	private String quanHuyen;
	private String thanhPho;
}
