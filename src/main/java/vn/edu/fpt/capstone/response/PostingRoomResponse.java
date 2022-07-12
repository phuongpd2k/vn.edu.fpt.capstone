package vn.edu.fpt.capstone.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.dto.PostDto;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PostingRoomResponse {
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
