package vn.edu.fpt.capstone.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HouseResponse {
	private String name;
	private String typeOfrental;
	private String verify;
}
