package vn.edu.fpt.capstone.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ROLE",
	uniqueConstraints = @UniqueConstraint(columnNames = "role"))
public class RoleModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ROLE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ROLE_SeqGen", sequenceName = "ROLE_Seq",allocationSize=1)
    private Long id;
    
    @Column(name = "ROLE")
    private String role;
    
    @ManyToMany(fetch = FetchType.LAZY, 
            cascade = CascadeType.ALL,
            mappedBy= "roles",
            targetEntity = UserModel.class)
    private Set<UserModel> users = new HashSet<UserModel>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<UserModel> getUsers() {
		return users;
	}

	public void setUsers(Set<UserModel> users) {
		this.users = users;
	}
    
}
