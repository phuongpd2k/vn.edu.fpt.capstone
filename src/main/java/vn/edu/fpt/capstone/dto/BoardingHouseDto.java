package vn.edu.fpt.capstone.dto;

import lombok.Data;

@Data
public class BoardingHouseDto {
    private Long id;
    private String name;
    private Long addressId;
    private String type;
    private Long userId;
    private boolean enable;
    private String description;
}
