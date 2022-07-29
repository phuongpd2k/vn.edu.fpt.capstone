package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import vn.edu.fpt.capstone.model.Auditable;

@Data
public class MapPositionDto extends Auditable<String>{
	@JsonProperty(index = 0)
    private Long id;
	
	@JsonProperty(index = 1)
    private String name;
    
	@JsonProperty(index = 2)
    private String longtitude;
    
	@JsonProperty(index = 3)
    private String latitude;
}
