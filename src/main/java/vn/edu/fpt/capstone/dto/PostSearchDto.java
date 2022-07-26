package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PostSearchDto extends SearchDto{
	@JsonProperty("postCode")
	private String postCode;
	
	@JsonProperty("username")
    private String username;
	
    @JsonProperty("fullname")
    private String fullname;
}
