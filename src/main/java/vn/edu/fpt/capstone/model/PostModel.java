package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString.Exclude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@Entity
@Table(name = "POST")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PostModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "POST_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "POST_SeqGen", sequenceName = "POST_Seq", allocationSize = 1)
	private Long id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
	private String description;
	@Column(name = "EXPIRED_TIME")
	private Long expiredTime;
	@Column(name = "IS_EXPIRED")
	private boolean isExpired = false;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	@JsonManagedReference
	private RoomModel room;
}
