package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ROOM")
public class RoomModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ROOM_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ROOM_SeqGen", sequenceName = "ROOM_Seq",allocationSize=1)
    private Long id;
    
    @Column(name = "BOARDING_HOUSEID")
    private Long boardingHouseId;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "AREA")
    private  String area;
    
    @Column(name = "MAXIMUM_NUMBER_OF_PEOPLE")
    private int maximumNumberOfPeople;
    
    @Column(name = "RENTAL_PRICE")
    private String rentalPrice;
    
    @Column(name = "DEPOSIT")
    private boolean deposit;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "ENABLE")
    private boolean enable;
    
    @Column(name = "ELECTRICITY_PRICE_BY_NUMBER")
    private String electricityPriceByNumber;
    
    @Column(name = "WATER_PRICE_PER_MONTH")
    private String waterPricePerMonth;
    
    @Column(name = "DESCRIPTION")
    private String description;
}
