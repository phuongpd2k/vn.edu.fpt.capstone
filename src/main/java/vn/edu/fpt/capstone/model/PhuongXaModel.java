package vn.edu.fpt.capstone.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "VN_XAPHUONGTHITRAN")
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
}
