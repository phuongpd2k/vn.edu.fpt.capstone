package vn.edu.fpt.capstone.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoardingHouseDto extends Auditable<String>{
    private Long id;
    private String name;
    private Long addressId;
    private String type;
    private Long userId;
    private boolean enable;
    private String description;
}
