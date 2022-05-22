package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IMAGE")
@EqualsAndHashCode(callSuper = false)
public class RoomTypeModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "IMAGE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "IMAGE_SeqGen", sequenceName = "IMAGE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "NAME")
    private String name;
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
    private String description;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
}
