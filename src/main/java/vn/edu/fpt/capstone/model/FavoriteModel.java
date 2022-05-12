package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "FAVORITE")
public class FavoriteModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "FAVORITE_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "FAVORITE_SeqGen", sequenceName = "FAVORITE_Seq",allocationSize=1)
    private Long id;
    @Column(name = "USERID")
    private Long userId;
    @Column(name = "ROOMID")
    private Long roomId;
}
