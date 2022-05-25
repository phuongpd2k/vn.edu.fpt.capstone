package vn.edu.fpt.capstone.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomRentalHistoryDto extends Auditable<String>{
	@JsonProperty(index = 0)
    private Long id;
	@JsonProperty(index = 1)
    private Long roomId;
	@JsonProperty(index = 2)
    private String roomRenterName;
	@JsonProperty(index = 3)
    private String roomRenterPhone;
	@JsonProperty(index = 4)
    private Date checkInDate;
	@JsonProperty(index = 5)
    private Date checkOutDate;
	@JsonProperty(index = 6)
    private String rentalPrice;
}
