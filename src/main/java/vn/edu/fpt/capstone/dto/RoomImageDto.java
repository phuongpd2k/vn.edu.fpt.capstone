package vn.edu.fpt.capstone.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomImageDto extends Auditable<String> {
	private Long id;
    private String name;
    private Long roomId;
    private Long imageId;
}
