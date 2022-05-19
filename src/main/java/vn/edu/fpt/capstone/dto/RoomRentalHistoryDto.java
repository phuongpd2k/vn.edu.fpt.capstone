package vn.edu.fpt.capstone.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomRentalHistoryDto extends Auditable<String>{
    private Long id;
    private Long roomId;
    private String roomRenterName;
    private String roomRenterPhone;
    private Date checkInDate;
    private Date checkOutDate;
    private String rentalPrice;
}
