package vn.edu.fpt.capstone.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.response.PostResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class FavoriteResponse {
	@JsonProperty("posts")
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" })
	List<PostResponse> postDtos;
}
