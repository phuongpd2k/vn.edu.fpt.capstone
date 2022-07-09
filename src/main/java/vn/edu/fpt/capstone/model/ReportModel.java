package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "REPORT")
@EqualsAndHashCode(callSuper = false)
public class ReportModel extends Auditable<String>{
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "REPORT_SeqGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "REPORT_SeqGen", sequenceName = "REPORT_Seq",allocationSize=1)
    private Long id;
    @Column(name = "USERID")
    private Long userId;
    
	@Column(name = "CONTENT", columnDefinition = "LONGTEXT NULL")
    private String content;
	
	@OneToOne
	@JoinColumn(name = "post_id")
    private PostModel post;
	
	
}
