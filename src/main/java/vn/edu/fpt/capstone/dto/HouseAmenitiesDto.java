package vn.edu.fpt.capstone.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class HouseAmenitiesDto extends Auditable<String>{
	 private Long id;
	    private Long houseId;
	    private Long amenityId;
}
