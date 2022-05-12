package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "BOARDING_HOUSE")
public class BoardingHouseModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "BOARDING_HOUSE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "BOARDING_HOUSE_SeqGen", sequenceName = "BOARDING_HOUSE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ADDRESSID")
    private Long addressId;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "USERID")
    private Long userId;
    @Column(name = "ENABLE")
    private boolean enable;
    @Column(name = "DESCRIPTION")
    private String description;
}
