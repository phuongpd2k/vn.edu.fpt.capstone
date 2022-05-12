package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FEEDBACK_LANDLORD")
public class FeedbackLandlordModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "FEEDBACK_LANDLORD_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "FEEDBACK_LANDLORD_SeqGen", sequenceName = "FEEDBACK_LANDLORD_Seq",allocationSize=1)
    private Long id;
    @Column(name = "USERID")
    private Long userId;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "NUMBER_OF_STAR")
    private int numberOfStar;
    @Column(name = "LANDLORDID")
    private Long landlordId;
}
