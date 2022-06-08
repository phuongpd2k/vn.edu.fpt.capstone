package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTypeDto extends Auditable<String>{
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("price")
	private int price;
}
