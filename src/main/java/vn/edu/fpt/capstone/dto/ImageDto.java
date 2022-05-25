package vn.edu.fpt.capstone.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImageDto extends Auditable<String>{
	@JsonProperty(index = 0)
    private Long id;
	@JsonProperty(index = 1)
    private String imageUrl;
}
