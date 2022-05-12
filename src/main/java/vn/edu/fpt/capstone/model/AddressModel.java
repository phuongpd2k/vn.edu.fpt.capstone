package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ADDRESS")
public class AddressModel {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "XAID")
    private Long xaId;
    @Column(name = "STREET")
    private String street;
    @Column(name = "LONGIUDE")
    private String longiude;
    @Column(name = "LATITUDE")
    private String latitude;

}
