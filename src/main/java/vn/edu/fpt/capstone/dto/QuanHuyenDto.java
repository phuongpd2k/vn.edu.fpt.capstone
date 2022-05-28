package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuanHuyenDto {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
	private String name;
	@JsonProperty(index = 2)
	private String type;
	@JsonProperty(index = 3)
	private Long maTp;
	@JsonProperty(index = 4)
	@JsonIgnoreProperties({ "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", "type","slug" })
	private ThanhPhoDto thanhPho;

}
