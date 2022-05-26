package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "Amenity")
@EqualsAndHashCode(callSuper = false)
public class AmenityModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "AMENITY_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "AMENITY_SeqGen", sequenceName = "AMENITY_Seq",allocationSize=1)
    private Long id;
    @Column(name = "TYPE")
    private String type;	
    @Column(name = "NAME")
    private String name;
    @Column(name = "ICON")
    private String icon;
    
    @ManyToMany(fetch = FetchType.LAZY, 
            cascade = CascadeType.MERGE,
            mappedBy= "amenities",
            targetEntity = RoomModel.class)
    @JsonBackReference
    private Set<RoomModel> rooms = new HashSet<RoomModel>();
}
