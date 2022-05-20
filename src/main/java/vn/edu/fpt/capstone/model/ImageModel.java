package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IMAGE")
@EqualsAndHashCode(callSuper = false)
public class ImageModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "IMAGE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "IMAGE_SeqGen", sequenceName = "IMAGE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
}
