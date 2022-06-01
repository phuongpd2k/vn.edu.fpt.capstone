package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@Entity
@Table(name = "ADDRESS")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AddressModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "ADDRESS_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ADDRESS_SeqGen", sequenceName = "ADDRESS_Seq",allocationSize=1)
    private Long id;
    @Column(name = "STREET")
    private String street;
    @Column(name = "LONGIUDE")
    private String longiude;
    @Column(name = "LATITUDE")
    private String latitude;
    
    @ManyToOne
	@JoinColumn(name = "phuongXa_id")
	private PhuongXaModel phuongXa;
    
//  @Column(name = "XAID")
//  private Long xaId;

}
