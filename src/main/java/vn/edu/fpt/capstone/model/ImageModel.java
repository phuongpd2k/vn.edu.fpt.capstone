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
@Table(name = "IMAGE")
@EqualsAndHashCode(callSuper = false)
public class ImageModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "IMAGE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "IMAGE_SeqGen", sequenceName = "IMAGE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    
    @ManyToMany(fetch = FetchType.LAZY, 
            cascade = CascadeType.MERGE,
            mappedBy= "images",
            targetEntity = RoomModel.class)
    private Set<RoomModel> rooms = new HashSet<RoomModel>();
}
