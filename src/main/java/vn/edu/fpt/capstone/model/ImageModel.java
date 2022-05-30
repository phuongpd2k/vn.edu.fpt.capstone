package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString.Exclude;

import java.util.Collection;

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
    
    @ManyToMany(mappedBy = "images")
    @EqualsAndHashCode.Exclude
    @Exclude
    @JsonBackReference
    private Collection<RoomModel> rooms;
}
