package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.edu.fpt.capstone.model.Auditable;

public class MapPositionDto extends Auditable<String>{
	@JsonProperty("id")
    private Long id;
	
	@JsonProperty("name")
    private String name;
    
	@JsonProperty("longiude")
    private String longiude;
    
	@JsonProperty("latitude")
    private String latitude;
}
