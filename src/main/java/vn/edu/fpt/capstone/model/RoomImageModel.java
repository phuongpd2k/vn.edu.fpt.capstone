package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ROOM_IMAGE")
@EqualsAndHashCode(callSuper = false)
public class RoomImageModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "ROOM_IMAGE_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ROOM_IMAGE_SeqGen", sequenceName = "ROOM_IMAGE_Seq", allocationSize = 1)
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ROOMID")
	private Long roomId;
	@Column(name = "IMAGEID")
	private Long imageId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private RoomModel room;
}
