package vn.edu.fpt.capstone.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "Thanh_Pho")
public class ThanhPhoModel {
	@Id
	@Column(name = "MATP")
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "SLUG")
	private String slug;
}
