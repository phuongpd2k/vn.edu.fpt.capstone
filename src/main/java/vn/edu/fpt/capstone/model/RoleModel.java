package vn.edu.fpt.capstone.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

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
    
    @OneToMany(mappedBy = "role")
    @JsonManagedReference
    private Set<UserModel> users = new HashSet<UserModel>();
}
