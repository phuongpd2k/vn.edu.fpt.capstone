package vn.edu.fpt.capstone.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class RoomRentalHistoryDto extends BaseDto{
    private Long id;
    private Long roomId;
    private String roomRenterName;
    private String roomRenterPhone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY")
    private String checkInDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY")
    private String checkOutDate;
    private String rentalPrice;
}
