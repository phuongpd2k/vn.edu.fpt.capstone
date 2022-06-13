package vn.edu.fpt.capstone.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(columnNames = "username") })
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserModel extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "USER_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "USER_SeqGen", sequenceName = "USER_Seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "IMAGE_LINK")
	private String imageLink;

	@Column(name = "GENDER")
	private boolean gender;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "isActive")
	private boolean isActive;

	@Column(name = "isVerify")
	private boolean isVerify;

	@ManyToOne
	@JoinColumn(name = "role_id")
	@JsonBackReference
	private RoleModel role;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<HouseModel> house;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<TransactionModel> transaction;

	@Column(name = "verification_code", updatable = false)
	private String verificationCode;

	@Column(name = "reset_code")
	private String resetCode;

	@Column(name = "balance")
	private float balance;

	@Column(name = "ENABLE", nullable = false)
	private boolean enable = true;
}
