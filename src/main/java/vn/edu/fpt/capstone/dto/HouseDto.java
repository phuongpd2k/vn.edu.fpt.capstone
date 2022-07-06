package vn.edu.fpt.capstone.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class HouseDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
	private String name;
	@JsonProperty(index = 2)
	private boolean enable = true;
	@JsonProperty(index = 3)
	private String phoneNumber;
	@JsonProperty(index = 4)
	private int area;
	@JsonProperty(index = 5)
	private String houseDirection;
	@JsonProperty(index = 6)
	private String description;
	@JsonProperty(index = 7)
	private String imageUrl;
	@JsonProperty(index = 8)
	@JsonIgnoreProperties({ "email", "username", "imageLink", "role", "delete", "active", "dob", "gender",
			"phoneNumber", "lastName", "firstName", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate","verify","balance" })
	private UserDto user;
	@JsonProperty(index = 9)
	private RoomDetails roomDetails;

	@JsonProperty(index = 10)
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" })
	private AddressDto address;
	@JsonProperty(index = 11)
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", "type", "icon" })
	private List<AmenityDto> amenities = new ArrayList<AmenityDto>();
	@JsonProperty(index = 12)
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", "description",
			"imageUrl" })
	private TypeOfRentalDto typeOfRental;
	@JsonProperty(index = 13)
	private String linkFb;
	@JsonProperty(index = 14)
	private String longtitude;
	@JsonProperty(index = 15)
	private String latitude;
//	private Long userId;
//	private Long addressId;
//	private Long typeOfRentalId;
}
