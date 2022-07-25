package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;
import vn.edu.fpt.capstone.response.PostResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReportDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
	private Long userId;
	@JsonProperty(index = 2)
	private String content;
	@JsonProperty(index = 3)
	@JsonIgnoreProperties({ "house", "roomDetails", "address", "amenities", "typeOfRental", "createdBy", "createdDate",
			"lastModifiedBy", "room" })
	private PostDto post;
	private PostResponse postdetail;

	public ReportDto() {
		super();
	}

	public ReportDto(Long userId, String content, PostDto post) {
		super();
		this.userId = userId;
		this.content = content;
		this.post = post;
	}
	
	public ReportDto(Long userId, String content, PostResponse postDetail) {
		super();
		this.userId = userId;
		this.content = content;
		this.postdetail = postDetail;
	}

	public ReportDto(Long id, Long userId, String content, PostDto post) {
		super();
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.post = post;
	}

}
