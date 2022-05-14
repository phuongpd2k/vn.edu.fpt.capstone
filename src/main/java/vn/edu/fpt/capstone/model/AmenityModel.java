package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Amenity")
public class AmenityModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "AMENITY_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "AMENITY_SeqGen", sequenceName = "AMENITY_Seq",allocationSize=1)
    private Long id;
    @Column(name = "ROOMID")
    private Long roomId;
    @Column(name = "PRIVATE_TOILET")
    private boolean privateToilet;
    @Column(name = "WIFI")
    private boolean wifi;
    @Column(name = "FREE")
    private boolean free;
    @Column(name = "BED")
    private boolean bed;
    @Column(name = "WARDROBE")
    private boolean wardrobe;
    @Column(name = "PARKING")
    private boolean parking;
    @Column(name = "FRIDGE")
    private boolean fridge;
    @Column(name = "AIR_CONDITIONER")
    private boolean airConditioner;
    @Column(name = "TIVI")
    private boolean tivi;
    @Column(name = "GUARD")
    private boolean guard;
    @Column(name = "KITCHEN")
    private boolean kitchen;
    @Column(name = "FINGER_PRINT_LOCK")
    private boolean fingerPrintLock;
    @Column(name = "ELECTRIC_WATER_HEATER")
    private boolean electricWaterHeater;
}
