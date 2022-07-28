package vn.edu.fpt.capstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "MAP_POSITION")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MapPositionModel extends Auditable<String>{
	@Id
    @Column(name = "ID")
    @GeneratedValue(generator = "MAP_POSITION_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "MAP_POSITION_SeqGen", sequenceName = "MAP_POSITION_Seq",allocationSize=1)
    private Long id;
	
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "LONGIUDE")
    private String longiude;
    
    @Column(name = "LATITUDE")
    private String latitude;
}
