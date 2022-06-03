package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class PostDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
	private String title;
	@JsonProperty(index = 2)
	private String description;
	@JsonProperty(index = 3, access = Access.READ_ONLY)
	private Long expiredTime;
	@JsonProperty(namespace = "isExpired", index = 4)
	private boolean isExpired = false;
	@JsonProperty(index = 5)
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" })
	private RoomDto room;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long numberOfDays = 0L;
}
