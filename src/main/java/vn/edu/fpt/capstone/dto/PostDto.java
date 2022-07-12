package vn.edu.fpt.capstone.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
public class PostDto extends Auditable<String> {
	private Long id;
	private HouseDto house;
	private RoomDto room;
	private PostTypeDto postType;
	private Date startDate;
	private Date endDate;
	private int numberOfDays;
	private int cost;
	private String status;
	private String note;
	private String verify = "UNVERIFIED";

	public PostDto() {
		super();
	}

	public PostDto(Long id) {
		super();
		this.id = id;
	}

	public PostDto(Long id, HouseDto house, RoomDto room, PostTypeDto postType, Date startDate, Date endDate,
			int numberOfDays, int cost, String status, String note, String verify) {
		super();
		this.id = id;
		this.house = house;
		this.room = room;
		this.postType = postType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberOfDays = numberOfDays;
		this.cost = cost;
		this.status = status;
		this.note = note;
		this.verify = verify;
	}

	private String post_type;
	private int postCost;
}
