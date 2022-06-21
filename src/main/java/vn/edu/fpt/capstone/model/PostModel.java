package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "house_id")
	@JsonManagedReference
	private HouseModel house;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	@JsonManagedReference
	private RoomModel room;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_type_id")
	@JsonManagedReference
	private PostTypeModel postType;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "NUMBER_OF_DAYS")
	private int numberOfDays;
	
	@Column(name = "COST")
	private int cost;
	
	@Column(name = "ENABLE", nullable = false)
	private boolean enable = true;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive = true;
	
	@Column(name = "status")
	private String status;
	
	
	@Column(name = "note")
	private String note;
	
	//@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
//	@JsonBackReference
//	@ToString.Exclude
    private TransactionModel transaction;
	
//	@Column(name = "TITLE")
//	private String title;
//	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
//	private String description;
//	@Column(name = "EXPIRED_TIME")
//	private Long expiredTime;
//	@Column(name = "IS_EXPIRED")
//	private boolean isExpired = false;
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "room_id")
//	@JsonManagedReference
//	private RoomModel room;
}
