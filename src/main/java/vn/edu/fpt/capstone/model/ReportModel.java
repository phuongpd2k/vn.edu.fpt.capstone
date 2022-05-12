package vn.edu.fpt.capstone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "REPORT")
public class ReportModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "REPORT_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "REPORT_SeqGen", sequenceName = "REPORT_Seq",allocationSize=1)
    private Long id;
    @Column(name = "USERID")
    private Long userId;
    @Column(name = "CONTENT")
    private String content;
}
