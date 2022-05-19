package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "ROOM_RENTAL_HISTORY")
@EqualsAndHashCode(callSuper = false)
public class RoomRentalHistoryModel extends BaseModel{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ROOM_RENTAL_HISTORY_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ROOM_RENTAL_HISTORY_SeqGen", sequenceName = "ROOM_RENTAL_HISTORY_Seq",allocationSize=1)
    private Long id;
    @Column(name = "ROOMID")
    private Long roomId;
    @Column(name = "ROOM_RENTER_NAME")
    private String roomRenterName;
    @Column(name = "ROOM_RENTER_PHONE")
    private String roomRenterPhone;
    @Column(name = "CHECK_IN_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date checkInDate;
    @Column(name="CHECK_OUT_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date checkOutDate;
    @Column(name = "RENTAL_PRICE")
    private String rentalPrice;
}
