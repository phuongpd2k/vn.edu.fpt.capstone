package vn.edu.fpt.capstone.dto;


import lombok.Data;
@Data
public class RoomRentalHistoryDto {
    private Long id;
    private Long roomId;
    private String roomRenterName;
    private String roomRenterPhone;
    private String checkInDate;
    private String checkOutDate;
    private String rentalPrice;
}
