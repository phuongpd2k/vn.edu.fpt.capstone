package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@OneToOne
	@JoinColumn(name = "address_id")
	private AddressModel address;
	@Column(name = "ENABLE")
	private boolean enable;
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
	private String description;
	@Column(name = "TYPEOFRENTALID")
	private Long typeOfRentalId;
	@Column(name = "USERID")
	private Long userId;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserModel user;
	@OneToMany(mappedBy = "house")
	@JsonBackReference
	private Set<RoomModel> room;
}
