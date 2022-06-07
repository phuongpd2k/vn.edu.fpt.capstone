package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "ROOM_TYPE")
@EqualsAndHashCode(callSuper = false)
public class RoomTypeModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ROOM_TYPE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ROOM_TYPE_SeqGen", sequenceName = "ROOM_TYPE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "NAME")
    private String name;
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
    private String description;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @OneToMany(mappedBy = "roomType")
    @JsonBackReference
    private Set<RoomModel> room;
    
    @Column(name = "ENABLE", nullable = false)
	private boolean enable = true;
}
