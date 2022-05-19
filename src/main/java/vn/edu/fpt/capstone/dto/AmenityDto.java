package vn.edu.fpt.capstone.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AmenityDto extends BaseDto{
    private Long id;
    private String type;	
    private String name;
    private String icon;
}
