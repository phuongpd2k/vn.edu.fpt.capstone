package vn.edu.fpt.capstone.model;


import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "QUAN_HUYEN")
public class QuanHuyenModel {
	@Id
	@Column(name = "MAQH")
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "MATP")
	private Long maTp;


}
