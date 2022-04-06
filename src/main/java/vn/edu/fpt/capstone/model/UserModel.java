package vn.edu.fpt.capstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "USER")
public class UserModel {
	
	@Id
	@GeneratedValue(generator = "USER_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "USER_SeqGen", sequenceName = "USER_Seq",allocationSize=1)
	@Column(name = "ID")
	private Long ID;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "IMAGE_LINK")
	private String imageLink;
	@Column(name = "GENDER")
	private boolean gender;
	@Column(name = "DOB")
	private String dob;
	
}
