package vn.edu.fpt.capstone.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.dto.RoomDto;

@Data
@EqualsAndHashCode(callSuper = false)
public class FavoriteResponse {
	@JsonProperty("rooms")
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" })
	List<RoomDto> roomDtos;
}
