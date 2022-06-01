package vn.edu.fpt.capstone.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "PHUONG_XA")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PhuongXaModel {
	@Id
	@Column(name = "XAID")
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "MAQH")
	private Long maQh;

	@OneToMany(mappedBy = "phuongXa")
	private List<AddressModel> addresses;
}
