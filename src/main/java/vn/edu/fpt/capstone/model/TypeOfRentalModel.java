package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "TYPE_OF_RENTAL")
@EqualsAndHashCode(callSuper = false)
public class TypeOfRentalModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "TYPE_OF_RENTAL_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "TYPE_OF_RENTAL_SeqGen", sequenceName = "TYPE_OF_RENTAL_Seq", allocationSize = 1)
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT NULL")
	private String description;
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	@OneToMany(mappedBy = "typeOfRental")
	@JsonManagedReference
	private List<HouseModel> houses;

}
