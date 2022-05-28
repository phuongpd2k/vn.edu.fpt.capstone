package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Data
@Entity
@Table(name = "HOUSE")
@EqualsAndHashCode(callSuper = false)
public class HouseModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "HOUSE_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "HOUSE_SeqGen", sequenceName = "HOUSE_Seq", allocationSize = 1)
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ENABLE")
	private boolean enable;
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
	private String description;
//	@ManyToMany(mappedBy = "houses")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "house_amenitiess", joinColumns = @JoinColumn(name = "house_id", insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "amenity_id", insertable = false, updatable = false))
	private List<AmenityModel> amenities = new ArrayList<AmenityModel>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeOfRental_id")
	private TypeOfRentalModel typeOfRental;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserModel user;

	@OneToMany(mappedBy = "house")
	private List<RoomModel> room;
	@OneToOne
	@JoinColumn(name = "address_id")
	private AddressModel address;

//	@Column(name = "TYPEOFRENTALID")
//	private Long typeOfRentalId;
//	@Column(name = "USERID")
//	private Long userId;
}
