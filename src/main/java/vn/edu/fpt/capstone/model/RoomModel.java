package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "ROOM")
@EqualsAndHashCode(callSuper = false)

public class RoomModel extends Auditable<String> {
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

//	@OneToMany(mappedBy = "room")
//	private List<RoomImageModel> roomImages = new ArrayList<>();

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "AREA")
	private String area;
	
	@Column(name = "MAXIMUM_NUMBER_OF_PEOPLE")
	private int maximumNumberOfPeople;
	
	@Column(name = "RENTAL_PRICE")
	private String rentalPrice;
	
	@Column(name = "DEPOSIT")
	private boolean deposit;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "ENABLE")
	private boolean enable;
	
	@Column(name = "ELECTRICITY_PRICE_BY_NUMBER")
	private String electricityPriceByNumber;
	
	@Column(name = "WATER_PRICE_PER_MONTH")
	private String waterPricePerMonth;
	
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
	private String description;
	
	@Column(name = "INTRO_IMAGE_URL")
	private String introImageUrl;
	
	@EqualsAndHashCode.Exclude
	@ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
            targetEntity=AmenityModel.class)
    @JoinTable(
            name = "room_amenity",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
	@JsonManagedReference
	private Set<AmenityModel> amenities = new HashSet<AmenityModel>();
	
	@EqualsAndHashCode.Exclude
	@ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
            targetEntity=ImageModel.class)
    @JoinTable(
            name = "room_images",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
	@JsonManagedReference
	private Set<ImageModel> images = new HashSet<ImageModel>();
	

//	@Column(name = "ROOMIMAGEID")
//	private Long roomImageId;
//	@Column(name = "HOUSEID")
//	private Long houseId;
//	@Column(name = "ROOMCATEGORYID")
//	private Long roomCategoryId;
//	@Column(name = "ROOMTYPEID")
//	private Long roomTypeId;
}
