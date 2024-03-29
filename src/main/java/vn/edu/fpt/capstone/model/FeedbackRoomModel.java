package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FEEDBACK_ROOM")
public class FeedbackRoomModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "FEEDBACK_ROOM_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "FEEDBACK_ROOM_SeqGen", sequenceName = "FEEDBACK_ROOM_Seq",allocationSize=1)
    private Long id;
    @Column(name = "USERID")
    private Long userId;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "NUMBER_OF_STAR")
    private int numberOfStar;
    @Column(name = "ROOMID")
    private Long roomId;
}
