package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "HOUSE")
@EqualsAndHashCode(callSuper = false)
public class HouseModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "HOUSE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "HOUSE_SeqGen", sequenceName = "HOUSE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ADDRESSID")
    private Long addressId;
    @Column(name = "ENABLE")
    private boolean enable;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TYPEOFRENTALID")
    private Long typeOfRentalId;
    @Column(name = "USERID")
    private Long userId;
}
