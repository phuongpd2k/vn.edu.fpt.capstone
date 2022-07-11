package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
@Data
@Entity
@Table(name = "FAVORITE")
@EqualsAndHashCode(callSuper = false)
public class FavoriteModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "FAVORITE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "FAVORITE_SeqGen", sequenceName = "FAVORITE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "USERID")
    private Long userId;
    @Column(name = "POSTID")
    private Long postId;
}
