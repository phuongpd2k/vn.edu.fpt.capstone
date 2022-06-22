package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "ROOM")
@EqualsAndHashCode(callSuper = false)

public class RoomModel extends Auditable<String> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "ROOM_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ROOM_SeqGen", sequenceName = "ROOM_Seq", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roomType_id")
	@JsonManagedReference
	private RoomTypeModel roomType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roomCategory_id")
	@JsonManagedReference
	private RoomCategoryModel roomCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "house_id")
	@JsonManagedReference
	private HouseModel house;

	@Column(name = "NAME")
	private String name;

	@Column(name = "AREA")
	private double area;

	@Column(name = "MAXIMUM_NUMBER_OF_PEOPLE")
	private int maximumNumberOfPeople;

	@Column(name = "RENTAL_PRICE")
	private int rentalPrice;

	@Column(name = "DEPOSIT")
	private int deposit;

	@Column(name = "STATUS")
	private boolean status_rental;

	@Column(name = "ENABLE")
	private boolean enable = true;

	@Column(name = "ELECTRICITY_PRICE_BY_NUMBER")
	private int electricityPriceByNumber;

	@Column(name = "WATER_PRICE_PER_MONTH")
	private int waterPricePerMonth;

	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
	private String description;

	@Column(name = "INTRO_IMAGE_URL")
	private String introImageUrl;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "room_amenity", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "amenity_id"))
	@JsonManagedReference
	private Collection<AmenityModel> amenities;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
	@JsonManagedReference
	private Collection<ImageModel> images;

	@Column(name = "FLOOR")
	private int floor;
	
	@Column(name = "TYPE")
	private String type;

	@OneToMany(mappedBy = "room")
	@JsonBackReference
	private List<PostModel> posts;
}
