package vn.edu.fpt.capstone.dto;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class PostDto extends Auditable<String> {
	private Long id;
	private HouseDto house;
	private RoomDto room;
	private PostTypeDto postType;
	private Date startDate;
	private Date endDate;
	private int numberOfDays;
	private int cost;
}
