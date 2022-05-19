package vn.edu.fpt.capstone.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TypeOfRentalDto extends BaseDto {
	private Long id;
	private String name;
	private String description;
	private String imageUrl;

}
