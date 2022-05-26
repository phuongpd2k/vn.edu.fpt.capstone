package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class HouseAmenitiesDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
//	@JsonProperty(index = 1)
//	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" })
//	private HouseDto house;
//	@JsonProperty(index = 2)
//	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate","id","house" })
//	private AmenityDto amenity;
	@JsonProperty(index = 1)
	private Long amenityId;
	@JsonProperty(index = 2)
	private Long houseId;
}
