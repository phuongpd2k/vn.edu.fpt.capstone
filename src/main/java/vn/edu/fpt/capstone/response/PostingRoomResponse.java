package vn.edu.fpt.capstone.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.RoomDto;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostingRoomResponse {
	@JsonIgnoreProperties({"postType","createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
	private PostDto post;
	
	@JsonIgnoreProperties({"house"})
	private List<RoomDto> rooms;
	
	private String street;
	private String phuongXa;
	private String quanHuyen;
	private String thanhPho;
}
