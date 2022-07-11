package vn.edu.fpt.capstone.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" })
public class ImageDto extends Auditable<String>{
	@JsonProperty(index = 0)
    private Long id;
	@JsonProperty("imageUrl")
    private String imageUrl;
}
