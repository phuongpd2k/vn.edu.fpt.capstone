package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString.Exclude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "AMENITY")
@EqualsAndHashCode(callSuper = false)
public class AmenityModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "AMENITY_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "AMENITY_SeqGen", sequenceName = "AMENITY_Seq", allocationSize = 1)
	private Long id;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ICON")
	private String icon;
	@ManyToMany(mappedBy = "amenities", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<HouseModel> houses = new ArrayList<HouseModel>();

	@ManyToMany(mappedBy = "amenities")
//    @EqualsAndHashCode.Exclude
//    @Exclude
	@JsonBackReference
	private Collection<RoomModel> rooms;

	@Column(name = "ENABLE", nullable = false)
	private boolean enable = true;
}
