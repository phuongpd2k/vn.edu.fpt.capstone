package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "HOUSE_AMENITIES")
@EqualsAndHashCode(callSuper = false)
public class HouseAmenitiesModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "HOUSE_AMENITIES_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "HOUSE_AMENITIES_SeqGen", sequenceName = "HOUSE_AMENITIES_Seq",allocationSize=1)
    private Long id;
    @Column(name = "HOUSEID")
    private Long houseId;
    @Column(name = "AMENITYID")
    private Long amenityId;

}
