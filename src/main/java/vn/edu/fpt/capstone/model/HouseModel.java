package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@Entity
@Table(name = "HOUSE")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class HouseModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "HOUSE_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "HOUSE_SeqGen", sequenceName = "HOUSE_Seq", allocationSize = 1)
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ENABLE")
	private boolean enable = true;
	@Column(name = "AREA")
	private int area = 0;
	@Column(name = "HOUSE_DIRECTION")
	private String houseDirection;
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
	private String description;
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "house_amenitiess", joinColumns = @JoinColumn(name = "house_id", insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "amenity_id", insertable = false, updatable = false))
	private List<AmenityModel> amenities = new ArrayList<AmenityModel>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeOfRental_id")
	@JsonBackReference
	private TypeOfRentalModel typeOfRental;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private UserModel user;

	@OneToMany(mappedBy = "house")
	@JsonBackReference
	private List<RoomModel> room;

	@OneToOne
	@JoinColumn(name = "address_id")
	private AddressModel address;

	@OneToMany(mappedBy = "house")
	@JsonBackReference
	private List<PostModel> posts;
}
