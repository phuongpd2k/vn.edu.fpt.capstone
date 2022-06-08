package vn.edu.fpt.capstone.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "POST_TYPE")
@EqualsAndHashCode(callSuper = false)
public class PostTypeModel extends Auditable<String>{
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "POST_TYPE_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "POST_TYPE_SeqGen", sequenceName = "POST_TYPE_Seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "PRICE")
	private int price;
	
	@Column(name = "ENABLE", nullable = false)
	private boolean enable = true;
	
	@OneToMany(mappedBy = "postType")
	@JsonBackReference
	private List<PostModel> posts;
}
