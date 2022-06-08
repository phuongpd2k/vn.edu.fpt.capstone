package vn.edu.fpt.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTypeDto extends Auditable<String>{
	private Long id;
	private String type;
	private int price;
}
