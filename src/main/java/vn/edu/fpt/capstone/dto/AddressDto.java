package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)

public class AddressDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 4)
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", "type", "maQh" })
	private PhuongXaDto phuongXa;
	@JsonProperty(index = 1)
	private String street;
	@JsonProperty(index = 2)
	private String longiude;
	@JsonProperty(index = 3)
	private String latitude;
//    private Long xaId;
}
