package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class HouseDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
	private String name;
	@JsonProperty(index = 2)
	private boolean enable;
	@JsonProperty(index = 3)
	private String description;
	@JsonProperty(index = 4)
	@JsonIgnoreProperties({ "role", "delete", "active", "dob", "gender", "phoneNumber", "lastName", "firstName",
			"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" })
	private UserDto user;
	@JsonProperty(index = 5)
	@JsonIgnoreProperties({"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
	private TypeOfRentalDto typeOfRental;
	@JsonProperty(index = 6)
	@JsonIgnoreProperties({"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
	private AddressDto address;
//	private Long userId;
//	private Long addressId;
//	private Long typeOfRentalId;
}
