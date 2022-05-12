package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ROLE")
public class RoleModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ROLE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ROLE_SeqGen", sequenceName = "ROLE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "NAME")
    private String name;
}
