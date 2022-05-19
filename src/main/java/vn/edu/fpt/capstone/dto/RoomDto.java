package vn.edu.fpt.capstone.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomDto extends BaseDto{
    private Long id;
    private Long boardingHouseId;
    private String type;
    private String name;
    private  String area;
    private int maximumNumberOfPeople;
    private String rentalPrice;
    private boolean deposit;
    private String status;
    private boolean enable;
    private String electricityPriceByNumber;
    private String waterPricePerMonth;
    private String description;
}
