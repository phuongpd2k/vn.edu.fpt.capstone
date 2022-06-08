//package vn.edu.fpt.capstone.model;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//@Table(name = "ROOM_AMENITIES")
//@EqualsAndHashCode(callSuper = false)
//public class RoomAmenitiesModel extends Auditable<String>{
//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(generator = "ROOM_AMENITIES_SeqGen", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "ROOM_AMENITIES_SeqGen", sequenceName = "ROOM_AMENITIES_Seq",allocationSize=1)
//    private Long id;
//    @Column(name = "ROOMID")
//    private Long houseId;
//    @Column(name = "AMENITYID")
//    private Long amenityId;
//
//}
