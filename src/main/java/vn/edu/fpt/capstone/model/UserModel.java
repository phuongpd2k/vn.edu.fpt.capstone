package vn.edu.fpt.capstone.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class UserModel extends Auditable<String>{
	@Id
	@GeneratedValue(generator = "USER_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "USER_SeqGen", sequenceName = "USER_Seq",allocationSize=1)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "IMAGE_LINK")
	private String imageLink;
	
	@Column(name = "GENDER")
	private boolean gender;
	
	@Column(name = "DOB")
	private Date dob;
	
	@Column(name = "isActive")
    private boolean isActive = true;
	
    @Column(name = "isDelete")
    private boolean isDelete = false;
	
	@ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;
	
}
