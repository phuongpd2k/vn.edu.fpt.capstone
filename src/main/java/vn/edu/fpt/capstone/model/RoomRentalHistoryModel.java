package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ROOM_RENTAL_HISTORY")
public class RoomRentalHistoryModel {
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
    private String checkInDate;
    @Column(name="CHECK_OUT_DATE")
    private String checkOutDate;
    @Column(name = "RENTAL_PRICE")
    private String rentalPrice;
}
