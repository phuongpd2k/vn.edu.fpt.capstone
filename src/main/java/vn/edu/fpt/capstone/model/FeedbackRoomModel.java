package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FEEDBACK_ROOM")
@EqualsAndHashCode(callSuper = false)
public class FeedbackRoomModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "FEEDBACK_ROOM_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "FEEDBACK_ROOM_SeqGen", sequenceName = "FEEDBACK_ROOM_Seq", allocationSize = 1)
	private Long id;
	@Column(name = "USERID", nullable = false)
	private Long userId;
	@Column(name = "CONTENT", columnDefinition = "LONGTEXT NULL", nullable = false)
	private String content;
	@Column(name = "RATING", nullable = false)
	private float rating;
	@Column(name = "ROOMID", nullable = false)
	private Long roomId;
}
