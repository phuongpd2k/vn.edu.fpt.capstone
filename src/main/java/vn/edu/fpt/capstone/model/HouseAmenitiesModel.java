//package vn.edu.fpt.capstone.model;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.ToString;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//@Table(name = "HOUSE_AMENITIES")
//@EqualsAndHashCode(callSuper = false)
//@ToString(callSuper=false)
//public class HouseAmenitiesModel extends Auditable<String>{
//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(generator = "HOUSE_AMENITIES_SeqGen", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "HOUSE_AMENITIES_SeqGen", sequenceName = "HOUSE_AMENITIES_Seq",allocationSize=1)
//    private Long id;
////    @ManyToOne(fetch = FetchType.LAZY)
////	@JoinColumn(name = "house_id")
////    private HouseModel house;
////    @ManyToOne
////	@JoinColumn(name = "amenity_id")
////    private AmenityModel amenity;
//    
//    @Column(name = "AMENITY_ID")
//    private Long amenityId;
//  @Column(name = "HOUSE_ID")
//  private Long houseId;
//}
