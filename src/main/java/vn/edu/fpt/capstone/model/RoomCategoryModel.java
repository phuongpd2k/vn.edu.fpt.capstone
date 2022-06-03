package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "ROOM_CATEGORY")
@EqualsAndHashCode(callSuper = false)
public class RoomCategoryModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ROOM_CATEGORY_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ROOM_CATEGORY_SeqGen", sequenceName = "ROOM_CATEGORY_Seq",allocationSize=1)
    private Long id;
    @Column(name = "NAME")
    private String name;
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
    private String description;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Column(name = "ICON")
	private String icon;
    @OneToMany(mappedBy = "roomCategory")
    @JsonBackReference
    private Set<RoomModel> room;
}
