package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class AmenityDto extends Auditable<String>{
	@JsonProperty(index = 0)
    private Long id;
	@JsonProperty(index = 1)
    private String type;	
	@JsonProperty(index = 2)
    private String name;
	@JsonProperty(index = 3)
    private String icon;
}
