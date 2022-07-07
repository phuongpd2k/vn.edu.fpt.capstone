package vn.edu.fpt.capstone.response;

import lombok.Data;

@Data
public class PostingResponse {
	private Long idRoom;
	private String nameHouse;
	private String nameRoom;
	private String imageUrl;
	private int minPrice;
	private int maxPrice;
	private float minArea;
	private float maxArea;
	private String street;
	private String phuongXa;
	private String quanHuyen;
	private String thanhPho;
	private String longtitude;
	private String latitude;
}
