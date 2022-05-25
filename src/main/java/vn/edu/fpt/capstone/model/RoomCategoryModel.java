package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import javax.persistence.*;

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
    @OneToMany(mappedBy = "roomCategory")
    private List<RoomModel> room;
}
